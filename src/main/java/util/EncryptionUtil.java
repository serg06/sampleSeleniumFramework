package util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.google.common.io.BaseEncoding;

public class EncryptionUtil {

    public static String encrypt(String plainText, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getAesKey(key));
            byte[] salt = new byte[8];
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.nextBytes(salt);
            cipher.update(salt);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("ISO-8859-1"));
            return BaseEncoding.base64Url().encode(encrypted);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedText, String key) {
        try {
            byte[] encrypted = BaseEncoding.base64Url().decode(encryptedText);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getAesKey(key));
            byte[] plain = cipher.doFinal(encrypted);
            if (plain == null || plain.length <= 8) {
                throw new RuntimeException("wrong encrypted text.");
            }
            byte[] data = new byte[plain.length - 8];
            System.arraycopy(plain, 8, data, 0, data.length);
            return new String(data, "ISO-8859-1");
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKeySpec getAesKey(String key) {
        final byte[] symKeyData = DatatypeConverter.parseHexBinary(key);
        return new SecretKeySpec(symKeyData, "AES");
    }
}