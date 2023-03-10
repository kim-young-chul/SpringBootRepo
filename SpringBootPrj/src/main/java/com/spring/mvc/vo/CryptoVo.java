/**
  * @파일명 : CryptoVo.java
  * @작성일 : 2023. 3. 3.
  * @작성자 : 김영철
  */
package com.spring.mvc.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.vo
 * @파일명 : CryptoVo.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
@Data
public class CryptoVo {

    /**
     * @필드타입 : String
     * @필드명 : version
     */
    private String version; // Current package version

    /**
     * @필드타입 : String
     * @필드명 : iv
     */
    private String iv; // Initialization vector

    /**
     * @필드타입 : Map<String,String>
     * @필드명 : keys
     */
    private Map<String, String> keys = new HashMap<>(); // Encrypted AES keys by RSA fingerprints

    /**
     * @필드타입 : String
     * @필드명 : cipher
     */
    private String cipher; // Actual encrypted message

    /**
     * @필드타입 : String
     * @필드명 : signature
     */
    private String signature; // Signature (Optional)
}
