/**
  * @파일명 : WebMvcConfig.java
  * @작성일 : 2023. 3. 10.
  * @작성자 : 김영철
  */
package com.spring.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/servlet/login_confirm")
                .addPathPatterns("/servlet/user_logou");

        registry.addInterceptor(new RootInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new ServletInterceptor())
                .addPathPatterns("/servlet/**")
                .excludePathPatterns("/servlet/user_login")
                .excludePathPatterns("/servlet/login_confirm")
                .excludePathPatterns("/servlet/user_logout");
    }
}
