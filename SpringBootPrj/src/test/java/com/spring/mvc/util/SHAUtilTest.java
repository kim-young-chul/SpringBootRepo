/**
  * @파일명 : SHAUtilTest.java
  * @작성일 : 2023. 3. 4.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.util
 * @파일명 : SHAUtilTest.java
 * @작성일 : 2023. 3. 4.
 * @작성자 : 김영철
 */
@SpringBootTest
class SHAUtilTest {

    @SpyBean
    private SHAUtil shaUtil;

    @Test
    final void testEncrypt() throws NoSuchAlgorithmException {
        String algorithm = "SHA-256";
        String input = "Hello World";
        String encrypt = shaUtil.encrypt(algorithm, input);
        assertNotNull(encrypt);
    }
}
