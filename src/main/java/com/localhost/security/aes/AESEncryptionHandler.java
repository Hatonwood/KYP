package com.localhost.security.aes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESEncryptionHandler {

    private static SecretKeySpec secretKey;
    private static final String passPhrase = ""; //Block of 16
    private static byte[] key;

    private final static Logger logger = LoggerFactory.getLogger(AESEncryptionHandler.class);

    public static void setKey(String myKey)
    {
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            logger.error("Error setting up the key: ", e);
        }
    }

    public static String encrypt(String strToEncrypt)
    {
        try {
            setKey(passPhrase);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.error("Error while encrypting: ", e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt)
    {
        try {
            setKey(passPhrase);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.error("Error while decrypting: ", e);
        }
        return null;
    }
}
