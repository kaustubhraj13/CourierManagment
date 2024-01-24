package util;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties loadProperties(String propertyFileName) {
        Properties properties = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + propertyFileName);
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return properties;
    }
}