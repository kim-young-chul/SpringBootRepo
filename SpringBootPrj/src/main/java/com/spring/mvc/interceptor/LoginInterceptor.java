package com.spring.mvc.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.interceptor
 * @파일명 : LoginInterceptor.java
 * @작성일 : 2023. 2. 17.
 * @작성자 : 김영철
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(LoginInterceptor.class);

    /**
     * @필드타입 : String
     * @필드명 : LOGIN
     */
    private static final String LOGIN = "login";
    /**
     * @필드타입 : String
     * @필드명 : GRADE
     */
    private static final String GRADE = "grade";

    /**
     * @메소드타입 : LoginInterceptor
     */
    public LoginInterceptor() {
        super();
        LOG.debug("LoginInterceptor ... ");
    }

    /**
     * @메소드타입 : LoginInterceptor
     * @메소드명 : preHandle
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        String userid = request.getParameter("userid");
        String userpw = request.getParameter("userpw");
        if (userid != null) {
            LOG.debug("userid ... {}", userid);
        }
        if (userpw != null) {
            LOG.debug("userpw ... {}", userpw);
        }

        HttpSession session = request.getSession();
        LOG.trace("session.getId ... {}", session.getId());

        if (session.getAttribute(LOGIN) != null) {
            LOG.trace("LOGIN is not null ... ");
            session.removeAttribute(LOGIN);
            session.removeAttribute(GRADE);

        }
        return true;
    }

    /**
     * @메소드타입 : LoginInterceptor
     * @메소드명 : postHandle
     */
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        LOG.trace("session.getId ... {}", session.getId());

        ModelMap mm = modelAndView.getModelMap();
        UserDto userDto = null;
        userDto = (UserDto) mm.get("LoginSuccess");

        if (userDto != null && userDto.getUserid() != null && userDto.getUserpw() != null) {
            session.invalidate();
            session = request.getSession();

            LOG.trace("userDto.getUserid ... {}", userDto.getUserid());
            LOG.trace("userDto.getGrade ... {}", userDto.getGrade());

            session.setAttribute(LOGIN, userDto.getUserid());
            session.setAttribute(GRADE, userDto.getGrade());

            LOG.trace("session.getAttribute(LOGIN) ... {}", session.getAttribute(LOGIN));
            LOG.trace("session.getAttribute(GRADE) ... {}", session.getAttribute(GRADE));

            LOG.trace("LoginSuccess ... ");
        }
    }
}
