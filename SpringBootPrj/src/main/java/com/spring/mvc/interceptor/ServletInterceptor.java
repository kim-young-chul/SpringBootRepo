package com.spring.mvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.interceptor
 * @파일명 : ServletInterceptor.java
 * @작성일 : 2023. 2. 20.
 * @작성자 : 김영철
 */
@Slf4j
public class ServletInterceptor implements HandlerInterceptor {

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
     * @메소드타입 : ServletInterceptor
     */
    public ServletInterceptor() {
        super();
        log.info("ServletInterceptor ... ");
    }

    /**
     * @메소드타입 : ServletInterceptor
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
        HttpSession session = request.getSession();
        log.info("session.getId ... {}", session.getId());
        
        String uri = request.getRequestURI();
        
        log.info("session.getAttribute(LOGIN) ... {}", session.getAttribute(LOGIN));
        log.info("session.getAttribute(GRADE) ... {}", session.getAttribute(GRADE));
        
        if (session.getAttribute(LOGIN) == null || session.getAttribute(GRADE) == null) {
            log.info("session attribute null ... ");
            response.sendRedirect("/servlet/user_login");
            return false;
        }

        if (!session.getAttribute(GRADE).equals("manager") && (uri.equals("/servlet/notice_write")
                || uri.equals("/servlet/notice_update") || uri.equals("/servlet/notice_delete")
                || uri.equals("/servlet/insert_notice") || uri.equals("/servlet/update_notice"))) {
            response.sendRedirect("/servlet/user_login");
            return false;
        }
        return true;
    }
}
