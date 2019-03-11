package xxatcust.oracle.apps.sudoku.util;

import java.io.File;

import java.io.IOException;

import java.io.InputStream;

import java.util.HashMap;

import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.myfaces.trinidad.model.UploadedFile;

import org.xml.sax.SAXException;

public class XMLUtils {
    public XMLUtils() {
        super();
    }


    public static String validateXMLSchema(File xsdFile, File xmlFile) {
        boolean validated = true ;
        SchemaFactory factory =
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        //File xsdFile = new File(xsdPath) ;
        try {
            schema = factory.newSchema(xsdFile);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException e) {
           ADFUtils.routeExceptions(e);
        } catch (IOException e) {
            ADFUtils.routeExceptions(e);
        }
        return "Y";
    }


}
