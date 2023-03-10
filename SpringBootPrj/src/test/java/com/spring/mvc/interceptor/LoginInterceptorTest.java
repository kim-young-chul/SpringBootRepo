package com.spring.mvc.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dto.UserDto;

import jakarta.servlet.http.HttpSession;

class LoginInterceptorTest {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @BeforeEach
    void setUp() {
        loginInterceptor = new LoginInterceptor();
    }

    @Test
    final void testLoginInterceptor() {
        assertNotNull(loginInterceptor);
    }

    @Test
    final void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("userid", "hong-gi-dong2");
        request.addParameter("userpw", "hong-password");
        HttpSession session = request.getSession();
        session.setAttribute("login", "hong-gi-dong2");
        boolean result = loginInterceptor.preHandle(request, response, null);
        assertTrue(result);
    }

    @Test
    final void testPostHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView mav = new ModelAndView();
        UserDto userDto = new UserDto();
        userDto.setUserid("hong-gi-dong2");
        userDto.setUserpw("hong-password");
        userDto.setGrade("manager");
        mav.addObject("LoginSuccess", userDto);
        loginInterceptor.postHandle(request, response, null, mav);
        HttpSession session = request.getSession();
        assertEquals("manager", session.getAttribute("grade"));
    }
}
