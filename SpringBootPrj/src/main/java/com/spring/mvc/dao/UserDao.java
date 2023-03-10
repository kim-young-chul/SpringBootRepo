package com.spring.mvc.dao;

import org.apache.ibatis.annotations.Mapper;

import com.spring.mvc.dto.UserDto;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.dao
 * @파일명 : UserDao.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
@Mapper
public interface UserDao {

    /**
     * @메소드타입 : UserDao
     * @메소드명 : selectUser
     * @return : UserDto
     * @param idnumber
     */
    UserDto selectUser(int idnumber);

    /**
     * @메소드타입 : UserDao
     * @메소드명 : insertUser
     * @param userid
     * @param userpw
     * @param idnumber
     */
    void insertUser(String userid, String userpw, int idnumber);

    /**
     * @메소드타입 : UserDao
     * @메소드명 : userLogin
     * @return : UserDto
     * @param userDto
     */
    UserDto userLogin(UserDto userDto);
}
