/**
  * @파일명 : RSAUtilTest.java
  * @작성일 : 2023. 3. 3.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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
 * @파일명 : RSAUtilTest.java
 * @작성일 : 2023. 3. 3.
 * @작성자 : 김영철
 */
@SpringBootTest
class RSAUtilTest {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(RSAUtilTest.class);

    @SpyBean
    private RSAUtil rsaUtil;

    @Test
    final void testGeneratorKeyPair() throws NoSuchAlgorithmException, IOException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        String algorithm = "RSA";
        int keysize = 2048;
        KeyPair keyPair = rsaUtil.generatorKeyPair(algorithm, keysize);

        PublicKey publicKey = (PublicKey) keyPair.getPublic();
        PrivateKey privateKey = (PrivateKey) keyPair.getPrivate();
        String standardCipher = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        String input = "Hello World";
        byte[] bytesInput = input.getBytes(StandardCharsets.UTF_8);
        byte[] base64bytesInput = Base64.getEncoder().encode(bytesInput);
        String base64StrInput = new String(base64bytesInput, StandardCharsets.UTF_8);
        String rsaEncInput = rsaUtil.encrypt(standardCipher, publicKey, base64StrInput);

        String algorithm2 = "AES";
        SecretKey secretKey = rsaUtil.getSecretKey(standardCipher, privateKey, rsaEncInput, algorithm2);
        assertNotNull(secretKey);
    }
}
