import service.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class XmlParserApplication {
    public static void main(String[] args) throws IOException
            , XMLStreamException
            , TransformerException
            , InterruptedException {

        FileWatcherService fileWatcherService = new FileWatcherService();
        fileWatcherService.fileWatcherDirectory();
    }
}
