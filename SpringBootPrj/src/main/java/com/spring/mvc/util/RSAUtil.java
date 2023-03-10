package com.spring.mvc.util;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.openssl.PEMWriter;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.util
 * @파일명 : RSAUtil.java
 * @작성일 : 2023. 2. 20.
 * @작성자 : 김영철
 */
public class RSAUtil {

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(RSAUtil.class);

    /**
     * @메소드타입 : RSAUtil
     */
    public RSAUtil() {
        LOG.trace("RSAUtil ... ");
    }

    /**
     * @메소드타입 : RSAUtil
     * @메소드명 : generatorKeyPair
     * @return : KeyPair
     * @param algorithm
     * @param keysize
     */
    public KeyPair generatorKeyPair(final String algorithm, final int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keysize, new SecureRandom());
        return keyPairGenerator.genKeyPair();
    }

    /**
     * @메소드타입 : RSAUtil
     * @메소드명 : convertKeytoPEM
     * @return : String
     * @param key
     */
    public String convertKeytoPEM(final Key key) throws IOException {
        StringWriter stringWriter = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(stringWriter);
        pemWriter.writeObject(key);
        pemWriter.close();
        String pem = stringWriter.toString();
        stringWriter.close();
        return pem;
    }

    /**
     * @메소드타입 : RSAUtil
     * @메소드명 : getSecretKey
     * @return : SecretKey
     * @param standardCipher
     * @param key
     * @param input
     * @param algorithm
     */
    public SecretKey getSecretKey(final String standardCipher, final Key key, final String input,
            final String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] decodeInput = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(standardCipher);
        cipher.init(Cipher.PRIVATE_KEY, key);
        byte[] bytesKey = cipher.doFinal(decodeInput);
        return new SecretKeySpec(bytesKey, algorithm);
    }

    /**
     * @메소드타입 : RSAUtil
     * @메소드명 : encrypt
     * @return : String
     * @param standardCipher
     * @param key
     * @param input
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String encrypt(final String standardCipher, final Key key, final String input) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodeInput = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(standardCipher);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytesEncrypt = cipher.doFinal(decodeInput);
        return Base64.getEncoder().encodeToString(bytesEncrypt);
    }
}
