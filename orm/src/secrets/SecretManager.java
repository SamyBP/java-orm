package secrets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SecretManager {
    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/secrets/secrets.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }
}
