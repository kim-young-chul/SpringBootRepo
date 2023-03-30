/**
  * @파일명 : LoginRestController.java
  * @작성일 : 2023. 3. 11.
  * @작성자 : 김영철
  */
package com.spring.mvc.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nimbusds.jose.JOSEException;
import com.spring.mvc.dto.UserDto;
import com.spring.mvc.service.LoginService;
import com.spring.mvc.util.JwtUtil;
import com.spring.mvc.util.RSAUtil;
import com.spring.mvc.vo.KeyPairVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.controller
 * @파일명 : LoginRestController.java
 * @작성일 : 2023. 3. 21.
 * @작성자 : 김영철
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class LoginRestController {

    /**
     * @필드타입 : int
     * @필드명 : RSA_KEY_SIZE
     */
    static final int RSA_KEY_SIZE = 2048;

    /**
     * @필드타입 : KeyPairVo
     * @필드명 : keyPairVo
     */
    private KeyPairVo keyPairVo;

    /**
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @메소드타입 : LoginRestController
     */
    protected LoginRestController() throws NoSuchAlgorithmException, IOException {
        final RSAUtil rsaUtil = new RSAUtil();
        KeyPair keyPair = rsaUtil.generatorKeyPair("RSA", RSA_KEY_SIZE);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String pemPublicKey = rsaUtil.convertKeytoPEM(publicKey);
        keyPairVo = new KeyPairVo();
        keyPairVo.setPemPublicKey(pemPublicKey);
        keyPairVo.setPrivateKey(privateKey);
        log.info("LoginRestController ... ");
    }

    /**
     * @필드타입 : LoginService
     * @필드명 : loginService
     */
    @Autowired
    private LoginService loginService;

    /**
     * @메소드타입 : LoginRestController
     * @메소드명 : userLogin
     * @return : ResponseEntity<String>
     * @return
     * @throws JOSEException
     */
    @GetMapping("/user_login")
    public ResponseEntity<String> userLogin() throws JOSEException {
        return ResponseEntity.ok(keyPairVo.getPemPublicKey());
    }

    /**
     * @메소드타입 : LoginRestController
     * @메소드명 : userLogout
     * @return : ResponseEntity<String>
     * @return
     * @throws JOSEException
     */
    @GetMapping("/user_logout")
    public ResponseEntity<String> userLogout() {
        // 쿠키 삭제
        ResponseCookie cookie = ResponseCookie
                .from("jwt", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .domain("aihub.com")
                .sameSite("None")
                .build();
        log.info("cookie delete ... {}", cookie.toString());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("logoutSuccess");
    }

    /**
     * @메소드타입 : LoginRestController
     * @메소드명 : loginConfirm
     * @return : ResponseEntity<String>
     * @param userDto
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws ParseException
     * @throws JOSEException
     */
    @PostMapping("/login_confirm")
    public ResponseEntity<String> loginConfirm(UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException, JOSEException {

        log.info("userid ... {}", userDto.getUserid());
        log.info("userpw ... {}", userDto.getUserpw());

        // 비밀번호 복호화, 사용자 조회
        final PrivateKey privateKey = keyPairVo.getPrivateKey();
        final UserDto userDtoOut = loginService.loginConfirm(privateKey, userDto);

        // 로그인 성공 시
        if (userDtoOut != null) {

            // 토큰 생성
            JwtUtil jwtUtil = new JwtUtil();
            String jwtString = jwtUtil.createToken(userDtoOut.getUserid());

            // 쿠키 생성
            ResponseCookie cookie = ResponseCookie
                    .from("jwt", jwtString)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(600)
                    .domain("aihub.com")
                    .sameSite("None")
                    .build();
            log.info("cookie create ... {}", cookie.toString());

            // 로그인 성공 시 응답
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("loginSuccess");
        }
        // 로그인 실패 시 응답
        return ResponseEntity.ok("loginFailed");
    }
}
