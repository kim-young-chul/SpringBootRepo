package com.spring.mvc.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

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
     * @메소드타입 : LoginController
     */
    public LoginController() {
        LOG.trace("LoginController ... ");
    }

    /**
     * @메소드타입 : LoginController
     * @메소드명 : userLogin
     * @return : ModelAndView
     * @param session
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @GetMapping("/servlet/user_login")
    public ModelAndView userLogin(final HttpSession session) throws NoSuchAlgorithmException, IOException {

        final ModelAndView mav = new ModelAndView();

        KeyPairVo keyPairVo = loginService.userLogin();

        session.setAttribute("privateKey", keyPairVo.getPrivateKey());
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
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws ParseException
     */
    @PostMapping("/servlet/login_confirm")
    public ModelAndView loginConfirm(final HttpSession session, final UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException {
        final PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
        final ModelAndView mav = new ModelAndView();
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
