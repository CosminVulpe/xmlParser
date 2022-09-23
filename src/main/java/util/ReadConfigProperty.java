package util;

import java.io.*;
import java.util.Properties;

import static util.ExceptionMessage.FILE_NOT_FOUND;

/**
 * <h1>This class reads from the config.properties file the path properties
 * for input file path and output file path<h1/>
 */
public class ReadConfigProperty {

    private static final String configPropertiesPathFile = "src/main/resources/config.properties";

    public static String readConfigProperties(String propertyName) throws IOException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(configPropertiesPathFile);
        } catch (FileNotFoundException e) {
            throw new IOException(FILE_NOT_FOUND, e.getCause());
        }

        Properties properties = new Properties();
        properties.load(fileReader);
        return properties.getProperty(propertyName);
    }

}
