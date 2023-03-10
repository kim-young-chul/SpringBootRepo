/**
  * @파일명 : ConvertUtil.java
  * @작성일 : 2023. 3. 3.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.util
 * @파일명 : ConvertUtil.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
public class ConvertUtil {

    /**
     * @메소드타입 : ConvertUtil
     * @메소드명 : bytesToHex
     * @return : String
     * @param bytes
     */
    String bytesToHex(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
