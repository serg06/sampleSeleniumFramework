package util;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.*;

/**
 * Created by Serguei on 6/15/2017.
 */
public class FileUtil {

    private static final String configDir = System.getProperty("user.dir") + "/src/main/resources/config/";

    public static JSONObject loadConfig(String configName)  {
        final String path = configDir + configName;
        try {
            File f = new File(path);
            Assert.assertTrue(f.exists());

            InputStream is = new FileInputStream(path);
            String jsonTxt = IOUtils.toString(is);
            return new JSONObject(jsonTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
