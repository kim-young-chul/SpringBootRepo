/**
  * @파일명 : AESUtilTest.java
  * @작성일 : 2023. 3. 3.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.util
 * @파일명 : AESUtilTest.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
@SpringBootTest
class AESUtilTest {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(AESUtilTest.class);

    @SpyBean
    private AESUtil aesUtil;

    @Test
    final void testNoPadding() {

        String sample = "1234567890";
        byte[] b = new byte[1];
        b[0] = 0x03;
        String pad = new String(b);
        String input = sample + pad;
        String result = aesUtil.noPadding(input);
        assertEquals("12345678", result);
    }

    /**
     * @메소드타입 : AESUtilTest
     * @메소드명 : testAES
     * @return : void
     */
    @Test
    final void testAES() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = aesUtil.generateKey("AES", 128);
        String standardCipher = "AES/CBC/PKCS5Padding";
        String plainText = "Hello World";
        byte[] base64Text = Base64.getEncoder().encode(plainText.getBytes());
        String base64strText = new String(base64Text, StandardCharsets.UTF_8);
        String base64Encrypt = aesUtil.encrypt(standardCipher, secretKey, base64strText);

        byte[] bytesIv = Base64.getEncoder().encode(aesUtil.getIv());
        String base64strIv = new String(bytesIv, StandardCharsets.UTF_8);
        String decryptText = aesUtil.decrypt(standardCipher, secretKey, base64strIv, base64Encrypt);
        assertEquals(plainText, decryptText);
    }
}
