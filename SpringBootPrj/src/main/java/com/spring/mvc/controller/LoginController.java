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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.spring.mvc.dto.UserDto;
import com.spring.mvc.service.LoginService;
import com.spring.mvc.util.RSAUtil;
import com.spring.mvc.vo.KeyPairVo;

import jakarta.servlet.http.HttpSession;

/**
 * @프로젝트명 : SecureCodingPrj
 * @패키지명 : com.spring.mvc.controller
 * @파일명 : LoginController.java
 * @작성일 : 2023. 3. 5.
 * @작성자 : 김영철
 */
@Controller
public class LoginController {

    /**
     * @필드타입 : int
     * @필드명 : RSA_KEY_SIZE
     */
    static final int RSA_KEY_SIZE = 2048;

    /**
     * @필드타입 : String
     * @필드명 : REUSRLOGIN
     */
    private static final String REUSRLOGIN = "redirect:user_login";

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    /**
     * @필드타입 : LoginService
     * @필드명 : loginService
     */
    @Autowired
    private LoginService loginService;

    /**
     * @필드타입 : KeyPairVo
     * @필드명 : keyPairVo
     */
    private KeyPairVo keyPairVo;

    /**
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @메소드타입 : LoginController
     */
    public LoginController() throws NoSuchAlgorithmException, IOException {
        LOG.trace("LoginController ... ");
        final RSAUtil rsaUtil = new RSAUtil();
        KeyPair keyPair = rsaUtil.generatorKeyPair("RSA", RSA_KEY_SIZE);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String pemPublicKey = rsaUtil.convertKeytoPEM(publicKey);
        keyPairVo = new KeyPairVo();
        keyPairVo.setPemPublicKey(pemPublicKey);
        keyPairVo.setPrivateKey(privateKey);
    }

    /**
     * @메소드타입 : LoginController
     * @메소드명 : userLogin
     * @return : ModelAndView
     * @param session
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    @GetMapping("/servlet/user_login")
    public ModelAndView userLogin(final HttpSession session) throws NoSuchAlgorithmException, IOException {

        final ModelAndView mav = new ModelAndView();
//        KeyPairVo keyPairVo;
//        if (session.getAttribute("keyPairVo") == null) {
//            keyPairVo = loginService.userLogin();
//            session.setAttribute("keyPairVo", keyPairVo);
//        } else {
//            keyPairVo = (KeyPairVo) session.getAttribute("keyPairVo");
//        }
        mav.addObject("base64PublicKey", keyPairVo.getPemPublicKey());
        mav.setViewName("user_login");
        return mav;
    }

    /**
     * @메소드타입 : LoginController
     * @메소드명 : userLogout
     * @return : String
     * @param session
     */
    @GetMapping("/servlet/user_logout")
    public String userLogout(final HttpSession session) {
        session.invalidate();
        return REUSRLOGIN;
    }

    /**
     * @메소드타입 : LoginController
     * @메소드명 : loginConfirm
     * @return : ModelAndView
     * @param session
     * @param userDto
     * @return
     * @throws ParseException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    @PostMapping("/servlet/login_confirm")
    public ModelAndView loginConfirm(final HttpSession session, final UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException {

//        KeyPairVo keyPairVo = (KeyPairVo) session.getAttribute("keyPairVo");
        final PrivateKey privateKey = keyPairVo.getPrivateKey();

        ModelAndView mav = new ModelAndView();
        final UserDto userDtoOut = loginService.loginConfirm(privateKey, userDto);
        if (userDtoOut == null) {
            mav.setViewName(REUSRLOGIN);
        } else {
            mav.setViewName("login_success");
            mav.addObject("LoginSuccess", userDtoOut);
        }
        return mav;
    }
}
