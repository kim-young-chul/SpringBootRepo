package com.spring.mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc
 * @파일명 : SpringBootPrjApplication.java
 * @작성일 : 2023. 3. 10.
 * @작성자 : 김영철
 */
@SpringBootApplication
@MapperScan(basePackages = "com.spring.mvc.dao")
class SpringBootPrjApplication {

    /**
     * @메소드타입 : SpringBootPrjApplication
     * @메소드명 : main
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(SpringBootPrjApplication.class, args);
    }

}
