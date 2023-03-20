/**
  * @파일명 : TestController.java
  * @작성일 : 2023. 3. 11.
  * @작성자 : 김영철
  */
package com.spring.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.controller
 * @파일명 : TestController.java
 * @작성일 : 2023. 3. 11.
 * @작성자 : 김영철
 */
@RestController
@RequestMapping("/api")
public class TestController {

    /**
     * @메소드타입 : TestController
     * @메소드명 : ip
     * @return : ResponseEntity<String>
     * @param request
     * @return
     */
    @PostMapping("/ip")
    public ResponseEntity<String> ip(HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        return ResponseEntity.ok(request.getRemoteAddr());
    }
}
