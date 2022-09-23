package service;

import util.ReadConfigProperty;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;
import static util.ExceptionMessage.FILE_NOT_FOUND;

/**
 * <h1>This class is responsible for watching the files which are imported notifying the user
 * what files are new and which files are deleted. <h1/>
 */
public class FileWatcherService {
    private final Logger LOG = Logger.getLogger(FileWatcherService.class.getName());
    private final String directoryFilePath;

    public FileWatcherService() throws IOException {
        this.directoryFilePath = ReadConfigProperty.readConfigProperties("inputPathFile");
    }

    /**
     * This method is constantly watching after new files to be received in the input folder.
     * @throws IOException -> This exception is thrown if the file does not exist.
     * @throws InterruptedException -> This exception is thrown when a thread is interrupted while it's waiting, sleeping, or otherwise occupied..
     * @throws XMLStreamException -> This exception is thrown for unexpected processing errors.
     * @throws TransformerException -> This exception specifies an exceptional condition that occurred during the transformation process.
     */
    public void fileWatcherDirectory() throws IOException
            , InterruptedException
            , XMLStreamException
            , TransformerException {

        WatchService watchService;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directoryFilePath);
            path.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            throw new IOException(FILE_NOT_FOUND, e.getCause());
        }

        WatchKey watchKey;
        while ((watchKey = watchService.take()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                ReadXML readXML = new ReadXML();
                ProcessXML processXML = new ProcessXML();
                WriteXML writeXML = new WriteXML(processXML);
                DeleteXML deleteXML = new DeleteXML();

                String file = event.context().toString();
                if (validateFileName(file)) {
                    printLogInfo("The file is successfully received ".concat(file));
                    startFileWatcher(file, readXML, processXML, writeXML, deleteXML);
                } else {
                    printLogWarning("Incorrect file input: "
                            .concat(file)
                            .concat(" The orders file name pattern is orders##.xml, where ## are digits."));
                }
            }
            watchKey.reset();
        }
    }

    /**
     * This method starts the reading, processing, writing and deleting process of the XML file.
     * @param file -> It specifies which file will be read, process, write and delete at the end.
     * @param readXML -> It is an instance of the ReadXML Class
     * @param processXML -> It is an instance of the ProcessXML Class
     * @param writeXML -> It is an instance of the WriteXML Class
     * @param deleteXML -> -> It is an instance of the DeleteXML Class
     * @throws XMLStreamException -> This exception is thrown for unexpected processing errors.
     * @throws IOException -> This exception is thrown if the file does not exist.
     * @throws TransformerException -> This exception specifies an exceptional condition that occurred during the transformation process.
     */
    private void startFileWatcher(String file
            , ReadXML readXML
            , ProcessXML processXML
            , WriteXML writeXML
            , DeleteXML deleteXML) throws XMLStreamException, IOException, TransformerException {

        XMLStreamReader readFile = readXML.readXML(Path.of(file));
        printLogInfo(String.format("The XML File: %s was read", file));

        processXML.processXML(readFile);
        printLogInfo(String.format("The XML File: %s was processed", file));

        if (processXML.isXMLProcessFinished()) {
            writeXML.exportXMLFile(file);
            printLogInfo(String.format("The XML File: %s was exported", file));

            deleteXML.deleteXMLAfterProcessing(readXML.getInputFilePath() + file);
            printLogWarning(String.format("The original XML File: %s was deleted", file));
        }
    }

    /**
     * This method checks if the imported file's name starts with 'orders' following two digits
     * and ends with xml extension
     * @param fileName -> It is the name of the File name
     * @return Nothing.
     */
    public boolean validateFileName(String fileName) {
        return (fileName.startsWith("orders")
                && Character.isDigit(fileName.charAt(6))
                && Character.isDigit(fileName.charAt(7))
                && fileName.endsWith(".xml"));
    }

    public String getDirectoryFilePath() {
        return directoryFilePath;
    }

    private void printLogWarning(String message) {
        LOG.warning(message);
    }

    private void printLogInfo(String message) {
        LOG.info(message);
    }
}