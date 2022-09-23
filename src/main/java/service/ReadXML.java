package service;

import util.ReadConfigProperty;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <h1>This class handles with the reading process of the XML file<h1/>
 */
public class ReadXML {
    private final String inputFilePath;

    public ReadXML() throws IOException {
        this.inputFilePath = ReadConfigProperty.readConfigProperties("inputPathFile");
    }

    /**
     * This method reads the XML file
     * @param path -> It is the path to the file read
     * @return -> Nothing.
     * @throws IOException -> This exception is thrown if the file does not exist.
     * @throws XMLStreamException -> This exception is thrown for unexpected processing errors.
     */
    public XMLStreamReader readXML(Path path) throws IOException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        return xmlInputFactory.createXMLStreamReader(
                Files.newInputStream(
                        Path.of(
                                inputFilePath.concat(String.valueOf(path))
                        )
                )
        );
    }

    public String getInputFilePath() {
        return inputFilePath;
    }
}
