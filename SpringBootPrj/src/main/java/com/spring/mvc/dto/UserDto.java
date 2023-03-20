package com.spring.mvc.dto;

import org.apache.ibatis.type.Alias;
import lombok.Data;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.dto
 * @파일명 : UserDto.java
 * @작성일 : 2023. 2. 18.
 * @작성자 : 김영철
 */
@Data
@Alias("userDto")
public class UserDto {

    /**
     * @필드타입 : int
     * @필드명 : id
     */
    private int id; // 일련번호

    /**
     * @필드타입 : String
     * @필드명 : userid
     */
    private String userid; // 사용자 아이디

    /**
     * @필드타입 : String
     * @필드명 : userpw
     */
    private String userpw; // 사용자 비밀번호

    /**
     * @필드타입 : String
     * @필드명 : grade
     */
    private String grade; // 등급
}
