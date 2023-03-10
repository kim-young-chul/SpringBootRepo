package com.spring.mvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootPrjApplicationTests {

    @Test
    void contextLoads() {
        final String temp = "helo";
        assertNotNull(temp);
    }

}
