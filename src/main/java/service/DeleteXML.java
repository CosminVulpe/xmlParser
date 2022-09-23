package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static util.ExceptionMessage.FILE_NOT_FOUND;

/**
 * <h1>This class is responsible for deleting the XML File after the reading, process and writing process. <h1/>
 */
public class DeleteXML {

    /**
     * <h2>This method handles the delete process of the XML File.<h2/>
     * @param filePath -> String. This parameter indicates the path of the file which will be deleted
     * @throws IOException -> if the XML File does not exist, the Exception is being thrown
     */
    public void deleteXMLAfterProcessing(String filePath) throws IOException {
        try {
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            throw new IOException(FILE_NOT_FOUND, e.getCause());
        }
    }

}
