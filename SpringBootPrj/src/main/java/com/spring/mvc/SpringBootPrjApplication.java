package com.spring.mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.spring.mvc.dao")
public class SpringBootPrjApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPrjApplication.class, args);
	}

}
