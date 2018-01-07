package com.rmz.cryptoutil;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Crypt methods class
 * Created by SBT-Mehdiev-RR on 19.04.2017.
 */
public class CryptoUtil {

    private Cipher ecipher;
    private Cipher dcipher;

    // Default cryptKey
    private static String cryptKey = "standartkey";

    // Default salt
    private static byte[] salt() {
        return new byte[]{0x49, 0x76, 0x61, 0x6e, 0x20, 0x4d, 0x65, 0x64, 0x76, 0x65, 0x64, 0x65, 0x76};
    }

    /**
     * Create new instance
     */
    public CryptoUtil() {
        this(cryptKey, salt());
    }

    /**
     * Create new instance
     *
     * @param keyString - cryptKey
     */
    public CryptoUtil(String keyString) {
        this(keyString, salt());
    }

    /**
     * Create new instance
     *
     * @param keyString - cryptKey
     * @param salt      - salt
     */
    public CryptoUtil(String keyString, byte[] salt) {
        try {
            setSecretKey(keyString, salt);
        } catch (InvalidParameterSpecException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Incorrent format keyString or salt!", ex);
        }
    }

    /**
     * Set secret key and init Cipher
     *
     * @param keyString - cryptKey
     * @param salt      - salt
     * @throws InvalidParameterSpecException      - Exception
     * @throws InvalidAlgorithmParameterException - Exception
     * @throws NoSuchAlgorithmException           - Exception
     * @throws InvalidKeyException                - Exception
     * @throws NoSuchPaddingException             - Exception
     * @throws InvalidKeySpecException            - Exception
     * @throws UnsupportedEncodingException       - Exception
     */
    private void setSecretKey(String keyString, byte[] salt) throws InvalidParameterSpecException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        Rfc2898DeriveBytes keyGenerator = new Rfc2898DeriveBytes(keyString, salt);

        byte[] keyBytes = keyGenerator.getBytes(32);
        byte[] ivBytes = keyGenerator.getBytes(16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        dcipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
    }

    /**
     * Encrypt method
     *
     * @param str - text string
     * @return - Base64 encrypt string
     */
    public String encrypt(String str) {
        try {
            byte[] utf8 = str.getBytes(StandardCharsets.UTF_16LE);
            byte[] enc = ecipher.doFinal(utf8);
            return Base64.getEncoder().encodeToString(enc);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Decrypt method
     *
     * @param str - Base64 encrypt string
     * @return - decrypt string
     */
    public String decrypt(String str) {
        try {
            byte[] dec = Base64.getDecoder().decode(str);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, StandardCharsets.UTF_16LE);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}