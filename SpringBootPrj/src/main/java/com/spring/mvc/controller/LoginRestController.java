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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
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
 * @작성일 : 2023. 3. 11.
 * @작성자 : 김영철
 */
@Slf4j
@RestController
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
     * @메소드명 : restUserLogin
     * @return : ResponseEntity<String>
     * @return
     */
    @GetMapping("/api/user_login")
    public ResponseEntity<String> restUserLogin() {
        return ResponseEntity.ok(keyPairVo.getPemPublicKey());
    }

    /**
     * @메소드타입 : LoginRestController
     * @메소드명 : loginConfirm
     * @return : ResponseEntity<String>
     * @param session
     * @param userDto
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws ParseException
     * @throws JsonProcessingException
     * @throws JOSEException 
     */
    @PostMapping("/api/login_confirm")
    public ResponseEntity<String> loginConfirm(UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException, JsonProcessingException, JOSEException {

        log.info("userid ... {}", userDto.getUserid());
        log.info("userpw ... {}", userDto.getUserpw());

        final PrivateKey privateKey = keyPairVo.getPrivateKey();
        final UserDto userDtoOut = loginService.loginConfirm(privateKey, userDto);

        String response = "loginFailed";
        if (userDtoOut != null) {
            response = "loginSuccess";
            JwtUtil jwtUtil = new JwtUtil();
            response = jwtUtil.createToken(userDtoOut.getUserid());
        }
        return ResponseEntity.ok(response);
    }
}
