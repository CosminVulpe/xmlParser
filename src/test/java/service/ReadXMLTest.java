package service;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class ReadXMLTest {

    private final ReadXML readXML = new ReadXML();

    public ReadXMLTest() throws IOException {
    }

    /***
     * <h2>This test method is checking if xml file does not exist in the input folder.<h2/>
     */
    @Test
    public void check_FileDoesNotExist_Should_Throw_FileNotFoundException() {
        assertThrows(IOException.class, () -> readXML.readXML(Path.of("orders23.xml")));
    }

}
