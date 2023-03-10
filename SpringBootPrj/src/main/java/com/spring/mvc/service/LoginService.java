package com.spring.mvc.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.simple.parser.ParseException;

import com.spring.mvc.dto.UserDto;
import com.spring.mvc.vo.KeyPairVo;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.service
 * @파일명 : LoginService.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
public interface LoginService {

    /**
     * @메소드타입 : LoginService
     * @메소드명 : userLogin
     * @return : KeyPairVo
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws Exception
     */
    KeyPairVo userLogin() throws NoSuchAlgorithmException, IOException;

    /**
     * @메소드타입 : LoginService
     * @메소드명 : loginConfirm
     * @return : UserDto
     * @param privateKey
     * @param userDto
     * @return
     * @throws ParseException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    UserDto loginConfirm(PrivateKey privateKey, UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException;
}
