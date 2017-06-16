package util;

import org.json.JSONObject;

/**
 * Created by Serguei on 6/15/2017.
 */
public class LoginCredentials {

    private static String encryptedUsername;
    private static String encryptedPassword;
    private static String key;

    static {
        JSONObject credentials = FileUtil.loadConfig("credentials.yaml");

        encryptedUsername = credentials.getString("encrypted_username");
        encryptedPassword = credentials.getString("encrypted_password");
        key = credentials.getString("key");
    }

    public static String getUsername() {
        return EncryptionUtil.decrypt(encryptedUsername, key);
    }

    public static String getPassword() {
        return EncryptionUtil.decrypt(encryptedPassword, key);
    }

}
