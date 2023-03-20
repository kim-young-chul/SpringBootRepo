/**
  * @파일명 : JwtUtil.java
  * @작성일 : 2023. 3. 19.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import com.nimbusds.jose.JOSEException;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.util
 * @파일명 : JwtUtil.java
 * @작성일 : 2023. 3. 19.
 * @작성자 : 김영철
 */
@SpringBootTest
class JwtUtilTest {

    /**
     * @필드타입 : JwtUtil
     * @필드명 : jwtUtil
     */
    @SpyBean
    private JwtUtil jwtUtil;

    /**
     * Test method for {@link com.spring.mvc.util.JwtUtilTest#createToken(byte[])}.
     * 
     * @throws ParseException
     * @throws JOSEException
     */
    @Test
    final void testCreateToken() throws ParseException, JOSEException {
        String userid = "ironman";
        String jwtString = jwtUtil.createToken(userid);
        String useridOut = jwtUtil.verifyToken(jwtString);
        assertEquals(userid, useridOut);
    }

}
