package service;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


/***
 * <h1>This test class is processing the XML file if the XML file exists in the input folder already.<h1/>
 */
public class ProcessXMLTest {

    private final ReadXML readXML = new ReadXML();
    private final ProcessXML processXML = new ProcessXML();

    public ProcessXMLTest() throws IOException {
    }


    @Test
    public void check_SuppliersListSize_Should_Equal_3() throws XMLStreamException, IOException {
        processXML.processXML(readXML.readXML(Path.of("orders23.xml")));

        int result = processXML.getSuppliers().size();
        int expected = 3;
        assertEquals(expected, result);
    }

    @Test
    public void check_OrderListSize_Should_Equal_2() throws XMLStreamException, IOException {
        processXML.processXML(readXML.readXML(Path.of("orders23.xml")));

        int result = processXML.getOrderList().size();
        int expected = 2;
        assertEquals(expected, result);
    }

    @Test
    public void check_ProcessingXMLFile_IsFinished_Should_Equal_True() throws XMLStreamException, IOException {
        processXML.processXML(readXML.readXML(Path.of("orders23.xml")));

        boolean result = processXML.isXMLProcessFinished();
        boolean expected = true;
        assertEquals(expected, result);
    }

}
