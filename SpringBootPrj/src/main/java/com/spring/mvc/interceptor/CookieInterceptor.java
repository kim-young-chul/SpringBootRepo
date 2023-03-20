/**
  * @파일명 : CookieInterceptor.java
  * @작성일 : 2023. 3. 20.
  * @작성자 : 김영철
  */
package com.spring.mvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.interceptor
 * @파일명 : CookieInterceptor.java
 * @작성일 : 2023. 3. 20.
 * @작성자 : 김영철
 */
@Slf4j
public class CookieInterceptor implements HandlerInterceptor {

    /**
     * @메소드타입 : CookieInterceptor
     * @메소드명 : postHandle
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        Cookie cookie = new Cookie("userid", "ironman");
        cookie.setMaxAge(600);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("aihub.com");
        cookie.setAttribute("SameSite", "None");
        log.info("cookie send ... {}", cookie.toString());
        response.addCookie(cookie);
    }

    /**
     * @메소드타입 : CookieInterceptor
     * @메소드명 : preHandle
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        try {
            Cookie[] cookie = request.getCookies();
            for (int i = 0; i < cookie.length; i++) {
                log.info("cookie name ... {}", cookie[i].getName());
                log.info("cookie value ... {}", cookie[i].getValue());
            }
        } catch (Exception e) {
            log.info("cookie not found ...");
        }
        return true;
    }
}
