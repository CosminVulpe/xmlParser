package service;

import org.junit.Test;
import util.ReadConfigProperty;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static util.ExceptionMessage.FILE_NOT_FOUND;

/**
 * <h1>This test class is testing if the properties from the config.properties file exists.<h1/>
 */
public class ReadConfigPropertyTest {

    @Test
    public void test_Validate_ReadConfig_Properties_InputFilePath_ShouldEqual_Notnull() throws IOException {
        String configProperties = ReadConfigProperty.readConfigProperties("inputPathFile");
        assertNotNull(configProperties);
    }

    @Test
    public void test_Validate_ReadConfig_Properties_OutputFilePath_ShouldEqual__Notnull() throws IOException {
        String configProperties = ReadConfigProperty.readConfigProperties("outputPathFile");
        assertNotNull(configProperties);
    }

    @Test
    public void test_Validate_FileNotFoundMessage_ShouldEqual_NotNull() {
        assertNotNull(FILE_NOT_FOUND);
    }
}
