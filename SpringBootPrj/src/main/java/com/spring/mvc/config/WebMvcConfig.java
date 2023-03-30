/**
  * @파일명 : WebMvcConfig.java
  * @작성일 : 2023. 3. 10.
  * @작성자 : 김영철
  */
package com.spring.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.mvc.interceptor.CookieInterceptor;
import com.spring.mvc.interceptor.CookieRestInterceptor;
import com.spring.mvc.interceptor.LoginInterceptor;
import com.spring.mvc.interceptor.RootInterceptor;
import com.spring.mvc.interceptor.ServletInterceptor;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc
 * @파일명 : WebMvcConfig.java
 * @작성일 : 2023. 3. 10.
 * @작성자 : 김영철
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @메소드타입 : WebMvcConfig
     * @메소드명 : addInterceptors
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new RootInterceptor())
                .addPathPatterns("/**");
        
        registry.addInterceptor(new CookieRestInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user_login")
                .excludePathPatterns("/api/login_confirm");
                
        registry.addInterceptor(new CookieInterceptor())
                .addPathPatterns("/servlet/user_login")
                .addPathPatterns("/servlet/notice_list");
        
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/servlet/login_confirm")
                .addPathPatterns("/servlet/user_logout");
        
        registry.addInterceptor(new ServletInterceptor())
                .addPathPatterns("/servlet/**")
                .excludePathPatterns("/servlet/user_login")
                .excludePathPatterns("/servlet/login_confirm")
                .excludePathPatterns("/servlet/user_logout");
    }

    /**
      * @메소드타입 : WebMvcConfig
      * @메소드명 : addCorsMappings
      * @param registry
      */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://aihub.com:3000", "http://localhost:3000")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowCredentials(true);
    }
}
