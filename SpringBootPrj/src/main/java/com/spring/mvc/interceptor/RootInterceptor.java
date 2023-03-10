package com.spring.mvc.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.interceptor
 * @파일명 : RootInterceptor.java
 * @작성일 : 2023. 2. 17.
 * @작성자 : 김영철
 */
public class RootInterceptor implements HandlerInterceptor {

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(RootInterceptor.class);

    /**
     * @메소드타입 : RootInterceptor
     */
    public RootInterceptor() {
        super();
        LOG.debug("RootInterceptor ... ");
    }

    /**
     * @메소드타입 : RootInterceptor
     * @메소드명 : preHandle
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        String theMethod = request.getMethod();
        LOG.trace("theMethod ... {}", theMethod);
        if (theMethod.equals("GET") || theMethod.equals("POST")) {
            // GET, POST methods are allowed
            return true;
        } else {
            // everything else is not allowed
            response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
            return false;
        }
    }

    /**
     * @메소드타입 : RootInterceptor
     * @메소드명 : postHandle
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
        response.setHeader("Allow", "POST, GET");
    }
}
