/**
  * @파일명 : LoginServiceTest.java
  * @작성일 : 2023. 2. 28.
  * @작성자 : 김영철
  */
package com.spring.mvc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.spring.mvc.dao.UserDao;
import com.spring.mvc.dto.UserDto;
import com.spring.mvc.util.AESUtil;
import com.spring.mvc.util.RSAUtil;
import com.spring.mvc.vo.KeyPairVo;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.service
 * @파일명 : LoginServiceTest.java
 * @작성일 : 2023. 2. 28.
 * @작성자 : 김영철
 */
@SpringBootTest
 class LoginServiceTest {

    private static final Logger LOG = LogManager.getLogger(LoginServiceTest.class);

    @MockBean
    private PrivateKey privateKey;

    @MockBean
    private UserDao userDao;

    @Autowired
    private LoginServiceImpl loginService;

    /**
     * Test method for
     * {@link com.spring.mvc.service.LoginServiceImpl#loginConfirm(PrivateKey, com.spring.mvc.dto.UserDto)}.
     * 
     * @throws Exception
     */
    @Test
    final void testUserLogin() throws Exception {
        /* Given */

        /* When */
        KeyPairVo keyPairVo = loginService.userLogin();
        /* Then */
        assertNotNull(keyPairVo);
    }

    @Test
    final void testLoginConfirm() throws Exception {

        UserDto userDto;
        UserDto userDtoOut;
        // 아이디, 비밀번호 모두 정상적인 경우
        /* Given */
        userDto = testLoginConfirmSub("hong-gi-dong2", "hong-password");
        /* When */
        when(userDao.userLogin(userDto)).thenReturn(new UserDto());
        userDtoOut = loginService.loginConfirm(this.privateKey, userDto);
        /* Then */
        assertNotNull(userDtoOut);

        // 아이디에 특수문자 입력 시
        /* Given */
        userDto = testLoginConfirmSub("<script>alert('hello');</script>", "hong-password");
        /* When */
        when(userDao.userLogin(userDto)).thenReturn(null);
        userDtoOut = loginService.loginConfirm(this.privateKey, userDto);
        /* Then */
        assertNull(userDtoOut);

        // 비밀번호에 특수문자 입력 시
        /* Given */
        userDto = testLoginConfirmSub("hong-gi-dong2", "<script>alert('hello');</script>");
        /* When */
        when(userDao.userLogin(userDto)).thenReturn(null);
        userDtoOut = loginService.loginConfirm(this.privateKey, userDto);
        /* Then */
        assertNull(userDtoOut);

        // 아이디, 비밀번호 모두 특수문자 입력 시
        userDto = testLoginConfirmSub("<script>alert('hello');</script>", "<script>alert('hello');</script>");
        /* When */
        when(userDao.userLogin(userDto)).thenReturn(null);
        userDtoOut = loginService.loginConfirm(this.privateKey, userDto);
        /* Then */
        assertNull(userDtoOut);
    }

    @SuppressWarnings("unchecked")
    UserDto testLoginConfirmSub(final String pUserId, final String pUserPw)
            throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException, ParseException {

        RSAUtil rsaUtil = new RSAUtil();
        AESUtil aesUtil = new AESUtil();

        String algorithm = "RSA";
        int keysize = 2048;
        KeyPair keyPair = rsaUtil.generatorKeyPair(algorithm, keysize);

        PublicKey publicKey = (PublicKey) keyPair.getPublic();
        PrivateKey privateKey = (PrivateKey) keyPair.getPrivate();
        this.privateKey = privateKey;

        SecretKey secretKey = aesUtil.generateKey("AES", 128);
        byte[] bytesSecKey = secretKey.getEncoded();
        byte[] base64SecKey = Base64.getEncoder().encode(bytesSecKey);
        String base64strSecKey = new String(base64SecKey, StandardCharsets.UTF_8);

        String standardCipher1 = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        String rsaEncInput = rsaUtil.encrypt(standardCipher1, publicKey, base64strSecKey);

        String standardCipher = "AES/CBC/PKCS5Padding";
        String plainText = pUserPw;
        byte[] base64Text = Base64.getEncoder().encode(plainText.getBytes());
        String base64strText = new String(base64Text, StandardCharsets.UTF_8);
        String base64Encrypt = aesUtil.encrypt(standardCipher, secretKey, base64strText);

        byte[] bytesIv = Base64.getEncoder().encode(aesUtil.getIv());
        String base64strIv = new String(bytesIv, StandardCharsets.UTF_8);

        String jsonString = "{\r\n" + "\"v\":\"hybrid-crypto-js_0.1.2\",\r\n"
                + "\"iv\": \"CmtyaZTyzoAp1mTNUTztic0v1...\",\r\n" + "\"keys\": {\r\n"
                + "\"d3:48:6a:e9:13...\":\"t9eds3...\"\r\n" + "},\r\n" + "\"cipher\":\"+iwVFsC2dECBQvwcm9DND...\"\r\n"
                + "\"signature\":\"sdL93kfdm12feds3C2...\"\r\n" + "}";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonString);
        LOG.debug("jsonObj.toString() ... {}", jsonObj.toString());

        JSONObject keys;
        jsonObj.replace("iv", base64strIv);
        jsonObj.replace("cipher", base64Encrypt);
        keys = (JSONObject) jsonObj.get("keys");
        keys.replace("d3:48:6a:e9:13...", rsaEncInput);
        LOG.debug("jsonObj.toString() ... {}", jsonObj.toString());

        byte[] bytes = Base64.getEncoder().encode(jsonObj.toString().getBytes(StandardCharsets.UTF_8));
        String strpasswd = new String(bytes, StandardCharsets.UTF_8);

        UserDto userDto = new UserDto();
        userDto.setUserid(pUserId);
        userDto.setUserpw(strpasswd);

        return userDto;
    }
}
