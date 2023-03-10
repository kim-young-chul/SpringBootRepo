package com.spring.mvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dto.UserDto;
import com.spring.mvc.service.LoginService;
import com.spring.mvc.vo.KeyPairVo;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
class LoginControllerTest {

    @MockBean
    private HttpSession session;

    @MockBean
    private LoginService loginService;

    @MockBean
    private KeyPairVo keyPairVo;

    @MockBean
    private PrivateKey privateKey;

    @MockBean
    private UserDto userDto;

    @Autowired
    private LoginController loginController;

    private static final String REUSRLOGIN = "redirect:user_login";

    @Test
    final void testUserLogin() throws NoSuchAlgorithmException, IOException {
        when(loginService.userLogin()).thenReturn(keyPairVo);
        when(keyPairVo.getPrivateKey()).thenReturn(privateKey);
        when(keyPairVo.getPemPublicKey()).thenReturn("pemPublickKey");
        ModelAndView mav = loginController.userLogin(session);

        assertEquals("user_login", mav.getViewName());
    }

    @Test
    final void testUserLogout() {
        String view = loginController.userLogout(session);
        verify(session, times(1)).invalidate();
        assertEquals(REUSRLOGIN, view);
    }

    @Test
    final void testLoginConfirm() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, ParseException {

        UserDto userDtoOut = new UserDto();

        when(session.getAttribute("privateKey")).thenReturn(privateKey);
        when(loginService.loginConfirm(privateKey, userDto)).thenReturn(null);
        ModelAndView mav = loginController.loginConfirm(session, userDto);
        assertEquals(REUSRLOGIN, mav.getViewName());

        when(loginService.loginConfirm(privateKey, userDto)).thenReturn(userDtoOut);
        mav = loginController.loginConfirm(session, userDto);
        assertEquals("login_success", mav.getViewName());
        assertNotNull(mav.getModelMap().get("LoginSuccess"), "LoginSuccess");
    }
}
