package xxatcust.oracle.apps.sudoku.util;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class StaxParser {
    public StaxParser() {
        super();
    }

    public static void StaxCreateXml(V93kQuote v93) {
        try {
            StringWriter stringWriter = new StringWriter();
            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = new IndentingXMLStreamWriter(xMLOutputFactory.createXMLStreamWriter(stringWriter));
               // xMLOutputFactory.createXMLStreamWriter(stringWriter);
            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeStartElement("V93000-Quote");

            xMLStreamWriter.writeStartElement("qheader");

            xMLStreamWriter.writeStartElement("qtitle");
            xMLStreamWriter.writeAttribute("", "Test Case 002 for Sudoku");

            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.flush();
            xMLStreamWriter.close();

            String xmlString = stringWriter.getBuffer().toString();

            stringWriter.close();

            System.out.println(xmlString);
        } catch (FactoryConfigurationError fce) {
            // TODO: Add catch code
            fce.printStackTrace();
        } catch (XMLStreamException xmlse) {
            // TODO: Add catch code
            xmlse.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
