/**
  * @파일명 : KeyPairVo.java
  * @작성일 : 2023. 3. 3.
  * @작성자 : 김영철
  */
package com.spring.mvc.vo;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.Data;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.vo
 * @파일명 : KeyPairVo.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
@Data
public class KeyPairVo {

    /**
     * @필드타입 : PublicKey
     * @필드명 : publicKey
     */
    private PublicKey publicKey;

    /**
     * @필드타입 : PrivateKey
     * @필드명 : privateKey
     */
    private PrivateKey privateKey;

    /**
     * @필드타입 : String
     * @필드명 : pemPublicKey
     */
    private String pemPublicKey;
}
