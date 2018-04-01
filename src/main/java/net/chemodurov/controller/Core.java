package net.chemodurov.controller;

import net.chemodurov.dao.TestDAO;
import net.chemodurov.model.Database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Core {
    private static Logger log = Logger.getLogger(Core.class.getName());
    private TestDAO testDAO;

    public Core(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public long doAllAndGetResult(long n){
        fillDatabase(n);
        Database database = new Database();
        database.setTests(testDAO.getAllField());
        createXML(database);
        convertXML("1.xml", "2.xml", "converter.xml");
        return getAverageFromXml("2.xml");

    }

    private void fillDatabase(long n) {
        //mark the execution time of the program.
        long start = System.currentTimeMillis();
        testDAO.deleteTable();
        testDAO.createTable();
        StringBuilder sb = new StringBuilder("(");
        for (int i = 1; i <= n; i++) {
            sb = sb.append(i).append("),(");
        }
        sb.delete(sb.length()-2, sb.length());
        testDAO.addManyValues(sb.toString());
        long finish = System.currentTimeMillis() - start;
        log.log(Level.INFO, "Fill database successful. Time: " + finish/1000000000 + " sec. " +
                "number of rows in db: " + testDAO.getNumberOfRows());
    }

    private void createXML(Database database){

        long start = System.currentTimeMillis();
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Database.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(database, new FileWriter("1.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "JAXB error.");
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "FileWriter error.");
        }
        long finish = System.currentTimeMillis() - start;
        log.log(Level.INFO, "Database converted to xml. Time: " + finish/1000000000 + "sec.");
    }

    private void convertXML(String inFileName, String outFileName, String pattern) {
        long start = System.currentTimeMillis();
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource(pattern));
            transformer.transform(new StreamSource(inFileName), new StreamResult(outFileName));
            log.log(Level.INFO, "Successful conversion. output file: " + outFileName);
        } catch (TransformerException e) {
            log.log(Level.WARNING, "Converter error.");
            throw new RuntimeException(e);
        }
        long finish = System.currentTimeMillis() - start;
        log.log(Level.INFO, "1.xml converted to 2.xml. Time: " + finish/1000000000 + "sec.");
    }

    private long getAverageFromXml(String filename) {
        long start = System.currentTimeMillis();
        long sum = 0;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filename));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("entry");
            for (int i = 0, cnt = nodeList.getLength(); i < cnt; i++) {
                Element element = (Element) nodeList.item(i);
                sum += Integer.parseInt(element.getAttribute("field"));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        long finish = System.currentTimeMillis() - start;
        log.log(Level.INFO, "the average value was obtained. Time: " + finish/1000000000 + "sec.");
        return sum;
    }
}
