package com.publicis.football.standings.config;

import com.publicis.football.standings.exception.StandingException;
import com.publicis.football.standings.model.Error;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtil {
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;

    public static String decrypt(String apiKey, String apiSecretKey, String apiSecretSalt) {
        try {
            byte[] encryptedData = Base64.getDecoder().decode(apiKey);
            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(apiSecretKey.toCharArray(), apiSecretSalt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

            byte[] cipherText = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, cipherText, 0, cipherText.length);

            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText, "UTF-8");
        } catch (Exception exception) {
            Error error = new Error();
            error.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMessage(exception.getMessage());
            throw new StandingException(exception.getMessage(),error);
        }
    }
}
