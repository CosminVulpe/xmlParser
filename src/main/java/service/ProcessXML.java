package service;

import model.Order;
import model.Price;
import model.Product;
import model.Supplier;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>This class is responsible for processing the XML file . <h1/>
 */
public class ProcessXML {
    private final List<Order> orderList;
    private final Set<String> suppliers;

    public ProcessXML() {
        this.orderList = new ArrayList<>();
        this.suppliers = new HashSet<>();
    }

    /**
     * This method handles the process of the XML file.
     * @param xmlStreamReader -> It is an instance of the class XMLStreamReader
     * @throws XMLStreamException -> This exception is thrown for unexpected processing errors.
     */
    public void processXML(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        int eventType;
        Product product = null;
        Order order = null;

        while (xmlStreamReader.hasNext()) {
            eventType = xmlStreamReader.next();

            if (eventType == XMLEvent.START_ELEMENT) {
                switch (xmlStreamReader.getName().getLocalPart()) {
                    case "order" -> {
                        String id = xmlStreamReader.getAttributeValue(null, "ID");
                        String timeOrderCreation = xmlStreamReader.getAttributeValue(null, "created");
                        order = new Order(id, LocalDateTime.parse(timeOrderCreation));
                    }
                    case "product" -> product = new Product();
                    case "description" -> {
                        eventType = xmlStreamReader.next();
                        if (checkProductIsNull(product) && checkEventTypeIsCharacters(eventType)) {
                            String description = xmlStreamReader.getText();
                            product.setDescription(description);
                        }
                    }
                    case "gtin" -> {
                        eventType = xmlStreamReader.next();
                        if (checkProductIsNull(product) && checkEventTypeIsCharacters(eventType)) {
                            String gtin = xmlStreamReader.getText();
                            product.setGtin(gtin);
                        }
                    }
                    case "price" -> {
                        String currency = xmlStreamReader.getAttributeValue(null, "currency");
                        eventType = xmlStreamReader.next();
                        if (checkProductIsNull(product) && checkEventTypeIsCharacters(eventType)) {
                            String priceValue = xmlStreamReader.getText();
                            product.setPrice(new Price(currency, Float.parseFloat(priceValue)));
                        }
                    }
                    case "supplier" -> {
                        eventType = xmlStreamReader.next();
                        if (checkProductIsNull(product) && checkEventTypeIsCharacters(eventType)) {
                            Supplier supplier = new Supplier(xmlStreamReader.getText());
                            product.setSupplier(supplier);
                            suppliers.add(supplier.getSupplierName());
                        }
                        if (checkProductIsNull(product) && checkOrderIsNull(order)) {
                            product.setOrderId(order.getOrderId());
                            order.addProduct(product);
                        }
                    }
                }
            }

            if (eventType == XMLEvent.END_ELEMENT) {
                if (xmlStreamReader.getName().getLocalPart().equals("order")) {
                    this.orderList.add(order);
                }
            }
        }
    }

    public boolean isXMLProcessFinished() {
        return orderList.size() != 0
                && suppliers.size() != 0;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Set<String> getSuppliers() {
        return suppliers;
    }

    private boolean checkEventTypeIsCharacters(int eventType) {
        return eventType == XMLEvent.CHARACTERS;
    }

    private boolean checkProductIsNull(Product product) {
        return product != null;
    }

    private boolean checkOrderIsNull(Order order) {
        return order != null;
    }
}
