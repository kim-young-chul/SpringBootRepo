/**
  * @파일명 : AESUtil.java
  * @작성일 : 2023. 3. 2.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @프로젝트명 : SecureCodingPrj
 * @패키지명 : com.spring.mvc.util
 * @파일명 : AESUtil.java
 * @작성일 : 2023. 3. 6.
 * @작성자 : 김영철
 */
public class AESUtil {

    /**
     * @필드타입 : int
     * @필드명 : IV_SIZE
     */
    private static final int IV_SIZE = 16;

    /**
     * @필드타입 : byte[]
     * @필드명 : gBytes
     */
    private byte[] gBytes;

    /**
     * @메소드타입 : AESUtil
     * @메소드명 : getIv
     * @return : byte[]
     * @return
     */
    public byte[] getIv() {
        return this.gBytes;
    }

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(AESUtil.class);

    /**
     * @메소드타입 : AESUtil
     * @메소드명 : generateKey
     * @return : SecretKey
     * @param algorithm
     * @param keysize
     * @return
     * @throws NoSuchAlgorithmException
     */
    public SecretKey generateKey(final String algorithm, final int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm); // "AES"
        keyGenerator.init(keysize); // 128
        return keyGenerator.generateKey();
    }

    /**
     * @메소드타입 : AESUtil
     * @메소드명 : encrypt
     * @return : String
     * @param standardCipher
     * @param key
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String encrypt(final String standardCipher, final Key key, final String input)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        byte[] decodeInput = Base64.getDecoder().decode(input);
        Cipher cipher = Cipher.getInstance(standardCipher);

        SecureRandom random = new SecureRandom();
        byte[] bytesIV = new byte[IV_SIZE];
        random.nextBytes(bytesIV); // Random initialization vector
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bytesIV);
        this.gBytes = bytesIV;

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encryptInput = cipher.doFinal(decodeInput);
        return Base64.getEncoder().encodeToString(encryptInput);
    }

    /**
     * @메소드타입 : AESUtil
     * @메소드명 : decrypt
     * @return : String
     * @param standardCipher
     * @param key
     * @param bytesIv
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String decrypt(final String standardCipher, final Key key, final String bytesIv, final String input)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decodeIv = Base64.getDecoder().decode(bytesIv);
        byte[] decodeInput = Base64.getDecoder().decode(input);
        Cipher cipher = Cipher.getInstance(standardCipher);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(decodeIv));
        byte[] decryptInput = cipher.doFinal(decodeInput);
        return new String(decryptInput, StandardCharsets.UTF_8);
    }

    /**
     * @메소드타입 : AESUtil
     * @메소드명 : noPadding
     * @return : String
     * @param cbcText
     * @return
     */
    public String noPadding(final String cbcText) {
        byte[] bytes = cbcText.getBytes();
        int paddingCnt = bytes[bytes.length - 1];
        LOG.debug("paddingCnt ... {}", paddingCnt);

        ConvertUtil convertUtil = new ConvertUtil();
        String hexValue = convertUtil.bytesToHex(bytes);
        LOG.debug("hexValue ... {}", hexValue);

        return cbcText.substring(0, bytes.length - paddingCnt);
    }
}
