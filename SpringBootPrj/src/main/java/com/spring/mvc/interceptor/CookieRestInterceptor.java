/**
  * @파일명 : CookieRestInterceptor.java
  * @작성일 : 2023. 3. 21.
  * @작성자 : 김영철
  */
package com.spring.mvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.mvc.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.interceptor
 * @파일명 : CookieRestInterceptor.java
 * @작성일 : 2023. 3. 21.
 * @작성자 : 김영철
 */
@Slf4j
public class CookieRestInterceptor implements HandlerInterceptor {

    /**
     * @메소드타입 : CookieRestInterceptor
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
        
        // 쿠키 확인
        String jwtString = null;
        try {
            Cookie[] cookie = request.getCookies();
            for (int i = 0; i < cookie.length; i++) {
                log.info("cookie name ... {}", cookie[i].getName());
                log.info("cookie value ... {}", cookie[i].getValue());
                if(cookie[i].getName().equals("jwt")) {
                    jwtString = cookie[i].getValue();
                }
            }
            
            // 쿠키가 있는 경우
            if(jwtString != null) {
                
                // 토큰 검증
                JwtUtil jwtUtil = new JwtUtil();
                String userid = jwtUtil.verifyToken(jwtString);
                
                // 토큰 검증에 성공한 경우
                if(userid != null) {
                    // 사용자 및 토큰을 조회하여 다시 비교해 본다.
                    log.info("userid ... {}", userid);
                    return true;
                }
            } else {
                // 쿠키가 없는 경우
                return false;
            }
        } catch (Exception e) {
            log.info("cookie not found ...");
            return false;
        }
        return true;
    }
}
