package com.spring.mvc.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.mvc.dao.UserDao;
import com.spring.mvc.dto.UserDto;
import com.spring.mvc.util.AESUtil;
import com.spring.mvc.util.RSAUtil;
import com.spring.mvc.util.SHAUtil;
import com.spring.mvc.vo.CryptoVo;
import com.spring.mvc.vo.KeyPairVo;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.service
 * @파일명 : LoginServiceImpl.java
 * @작성일 : 2023. 3. 4.
 * @작성자 : 김영철
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(LoginServiceImpl.class);

    /**
     * @필드타입 : int
     * @필드명 : RSA_KEY_SIZE
     */
    static final int RSA_KEY_SIZE = 2048;

    /**
     * @필드타입 : UserDao
     * @필드명 : userDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : loginConfirm
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
     */
    @Override
    public UserDto loginConfirm(final PrivateKey privateKey, final UserDto userDto)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException, ParseException {
        UserDto userDtoOut = null;
        String decryptedPw = decryptPw(privateKey, userDto);
        userDto.setUserpw(decryptedPw);
        LOG.debug("decryptedPw ... {}", decryptedPw);
        if (this.verifyPattern(userDto)) {
            String encryptedPw = this.encryptPw(userDto);
            userDto.setUserpw(encryptedPw);
            userDtoOut = userDao.userLogin(userDto);
        }
        return userDtoOut;
    }

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : parsPwJson
     * @return : CryptoVo
     * @param decodedString
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    private CryptoVo parsPwJson(final String decodedString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(decodedString);
        CryptoVo cryptoVo = new CryptoVo();
        cryptoVo.setVersion((String) jsonObj.get("v"));
        cryptoVo.setIv((String) jsonObj.get("iv"));
        JSONObject keysObj = (JSONObject) jsonObj.get("keys");
        Iterator<?> iterator = keysObj.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
            cryptoVo.getKeys().put("key", entry.getKey());
            cryptoVo.getKeys().put("value", entry.getValue());
        }
        cryptoVo.setCipher((String) jsonObj.get("cipher"));
        cryptoVo.setSignature((String) jsonObj.get("signature"));
        return cryptoVo;
    }

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : decryptPw
     * @return : String
     * @param privateKey
     * @param userDto
     * @return
     * @throws ParseException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    private String decryptPw(final PrivateKey privateKey, final UserDto userDto)
            throws ParseException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] decodedBytes = Base64.getDecoder().decode(userDto.getUserpw());
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        CryptoVo cryptoVo = parsPwJson(decodedString);

        RSAUtil rsaUtil = new RSAUtil();
        final String rsaCipher = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";

        final String aesEncSecKey = cryptoVo.getKeys().get("value");
        SecretKey aesSeckey = rsaUtil.getSecretKey(rsaCipher, privateKey, aesEncSecKey, "AES");

        AESUtil aesUtil = new AESUtil();
        final String aesCipher = "AES/CBC/NoPadding";
        final String aesIv = cryptoVo.getIv();
        final String aesEncText = cryptoVo.getCipher();
        String decryptedPw = aesUtil.decrypt(aesCipher, aesSeckey, aesIv, aesEncText);
        LOG.debug("decryptedPw ... {}", decryptedPw);

        return aesUtil.noPadding(decryptedPw);
    }

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : verifyPattern
     * @return : boolean
     * @param userDto
     * @return
     */
    private boolean verifyPattern(final UserDto userDto) {
        final boolean userIdResult = Pattern.matches("[A-Za-z0-9-_]*", userDto.getUserid());
        final boolean userPwResult = Pattern.matches("[A-Za-z0-9-_!@#%+]*", userDto.getUserpw());
        LOG.debug("userIdResult ... {}", userIdResult);
        LOG.debug("userPwResult ... {}", userPwResult);
        return userIdResult && userPwResult;
    }

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : userLogin
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @Override
    public KeyPairVo userLogin() throws NoSuchAlgorithmException, IOException {
        final RSAUtil rsaUtil = new RSAUtil();
        KeyPair keyPair = rsaUtil.generatorKeyPair("RSA", RSA_KEY_SIZE);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String pemPublicKey = rsaUtil.convertKeytoPEM(publicKey);
        KeyPairVo keyPairVo = new KeyPairVo();
        keyPairVo.setPemPublicKey(pemPublicKey);
        keyPairVo.setPrivateKey(privateKey);
        return keyPairVo;
    }

    /**
     * @메소드타입 : LoginServiceImpl
     * @메소드명 : encryptPw
     * @return : String
     * @param userDto
     * @return
     * @throws NoSuchAlgorithmException
     */
    private String encryptPw(final UserDto userDto) throws NoSuchAlgorithmException {
        final SHAUtil shaUtil = new SHAUtil();
        return shaUtil.encrypt("SHA-256", userDto.getUserpw());
    }
}
