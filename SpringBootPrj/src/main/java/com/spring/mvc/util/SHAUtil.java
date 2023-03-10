package com.spring.mvc.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @프로젝트명 : SecureCodingPrj
 * @패키지명 : com.spring.mvc.util
 * @파일명 : SHAUtil.java
 * @작성일 : 2023. 3. 6.
 * @작성자 : 김영철
 */
public class SHAUtil {

    /**
     * @메소드타입 : SHAUtil
     * @메소드명 : encrypt
     * @return : String
     * @param algorithm
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(final String algorithm, final String input) throws NoSuchAlgorithmException {
        byte[] decodeInput = input.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(decodeInput);
        ConvertUtil convertUtil = new ConvertUtil();
        return convertUtil.bytesToHex(messageDigest.digest());
    }
}
