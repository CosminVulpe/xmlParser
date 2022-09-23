package service;

import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;


/***
 * <h1>This test class is processing the XML file if is exists in the input folder already.<h1/>
 */
public class WriteXMLTest {

    private final ProcessXML processXML = new ProcessXML();

    private final WriteXML writeXML = new WriteXML(processXML);

    public WriteXMLTest() throws IOException {
    }

    @Test
    public void check_FormatXML_Trows_TransformerException() {
        assertThrows(TransformerException.class, () -> writeXML.formatXML(""));
    }

}
