package service;

import model.Order;
import model.Product;
import util.ReadConfigProperty;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>This class handles with the writing process of the XML file<h1/>
 */
public class WriteXML {
    private final ProcessXML processXML;
    private final HashMap<String, List<Product>> supplierListHashMap;
    private final String outputFilePath;

    public WriteXML(ProcessXML processXML) throws IOException {
        this.processXML = processXML;
        this.supplierListHashMap = new HashMap<>();
        this.outputFilePath = ReadConfigProperty.readConfigProperties("outputPathFile");
    }

    /**
     * This method occupies with the export part of the XML file.
     * @param file -> This is the file that is exporting.
     * @throws XMLStreamException -> This exception is thrown for unexpected processing errors.
     * @throws TransformerException -> This exception specifies an exceptional condition that occurred during the transformation process.
     * @throws IOException -> This exception is thrown if the file does not exist.
     */
    public void exportXMLFile(String file) throws XMLStreamException, TransformerException, IOException {
        sortDescendingByTimeStampAndPrice();

        String digits = file
                .replaceAll("[a-zA-Z /.]", "");

        for (Map.Entry<String, List<Product>> entry : supplierListHashMap.entrySet()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            writeXML(byteArrayOutputStream, entry.getValue());

            String xml = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
            String prettyPrintXML = formatXML(xml);

            Files.writeString(Paths.get(
                    outputFilePath
                            .concat(entry.getKey())
                            .concat(digits)
                            .concat(".xml")
            ), prettyPrintXML, StandardCharsets.UTF_8);
        }
    }

    public String formatXML(String XML) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamSource source = new StreamSource(new StringReader(XML));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString()
                .replaceFirst("<products", "\n<products");
    }

    private void sortDescendingByTimeStampAndPrice() {
        List<Product> sortedProductListByTimeStamp = processXML
                .getOrderList()
                .stream()
                .sorted(Comparator.comparing(
                        Order::getLocalDateTime
                ).reversed())
                .map(Order::getProductList)
                .collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        populateHashMap(sortedProductListByTimeStamp);

        for (Map.Entry<String, List<Product>> entry : supplierListHashMap.entrySet()) {
            entry.getValue()
                    .stream()
                    .map(x -> x.getPrice().getValue())
                    .collect(Collectors.toList())
                    .sort(Collections.reverseOrder());
        }
    }

    private void writeXML(OutputStream outputStream
            , List<Product> productList) throws XMLStreamException {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream);

        xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
        xmlStreamWriter.writeStartElement("products");

        productList
                .forEach(product -> {
                    try {
                        xmlStreamWriter.writeStartElement("product");

                        xmlStreamWriter.writeStartElement("description");
                        xmlStreamWriter.writeCharacters(product.getDescription());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("gtin");
                        xmlStreamWriter.writeCharacters(product.getGtin());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("price");
                        xmlStreamWriter.writeAttribute("currency", product.getPrice().getCurrency());
                        xmlStreamWriter.writeCharacters(String.valueOf(product.getPrice().getValue()));
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("orderid");
                        xmlStreamWriter.writeCharacters(product.getOrderId());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeEndElement();
                    } catch (XMLStreamException e) {
                        throw new RuntimeException(e.getCause());
                    }
                });
        xmlStreamWriter.writeEndDocument();

        xmlStreamWriter.flush();
        xmlStreamWriter.close();
    }

    private void populateHashMap(List<Product> productList) {
        for (String supplier : processXML.getSuppliers()) {
            supplierListHashMap.put(
                    supplier, productList
                            .stream()
                            .filter(product -> product.getSupplier().getSupplierName().equals(supplier))
                            .collect(Collectors.toList())
            );
        }
    }

}
