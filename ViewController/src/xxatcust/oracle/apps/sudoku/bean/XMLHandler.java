package xxatcust.oracle.apps.sudoku.bean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.JSONUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.util.XMLUtils;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.XMLParentElement;

public class XMLHandler {
    public XMLHandler() {
    }

    public void fileUploadVCE(ValueChangeEvent vce) {
        System.out.println("Inside Value Listener");
        if (vce.getNewValue() != null) {
            //Get File Object from VC Event
            UploadedFile fileVal = (UploadedFile)vce.getNewValue();
            //Upload File to path- Return actual server path
            String path = uploadFile(fileVal);
            System.out.println(fileVal.getContentType());
            //Method to insert data in table to keep track of uploaded files
//            OperationBinding ob = executeOperation("setFileData");
//            ob.getParamsMap().put("name", fileVal.getFilename());
//            ob.getParamsMap().put("path", path);
//            ob.getParamsMap().put("contTyp", fileVal.getContentType());
//            ob.execute();
            // Reset inputFile component after upload
            ResetUtils.reset(vce.getComponent());
        }
    }
    
    /**Method to upload file to actual path on Server*/
    private String uploadFile(UploadedFile file) {

        UploadedFile myfile = file;
        String path = null;
        if (myfile == null) {

        } else {
            // All uploaded files will be stored in below path
            path = "D://FileStore//Uploads//" + myfile.getFilename();
            InputStream inputStream = null;
            try {
                FileOutputStream out = new FileOutputStream(path);
                inputStream = myfile.getInputStream();
                byte[] buffer = new byte[8192];
                int bytesRead = 0;
                while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                out.close();
            } catch (Exception ex) {
                // handle exception
                ex.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }

        }
        //Returns the path where file is stored
        return path;
    }
    
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = ADFUtils.findOperation(operation);
        return createParam;

    }

    public void validateXML(ActionEvent actionEvent) {
        //Added comments
        String validated;
        File f = new File("D://Projects//Advantest//Xml_samples/V93000 C&Q 3.0 - XML File Example.xml");
                try {
                    validated = XMLUtils.validateXMLSchema("D://Projects//Advantest//Xml_samples//V93000 C&Q 3.0 - XML File Schema.xsd",f );
                    if(validated!=null && validated.equalsIgnoreCase("Y")){
                        parseXMLToPojo(null);
                    }
                    else{
                ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, validated);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public void parseXMLToPojo(ActionEvent actionEvent) {
        V93kQuote parent = JaxbParser.jaxbXMLToObject();
        String jsonStr = JSONUtils.convertObjToJson(parent);
        Object obj = JSONUtils.convertJsonToObject(jsonStr);
        XMLImportPageBean impBean = new XMLImportPageBean();
        impBean.setAllPmf(parent.getConfigObject().getPmfObject().getPmfMap());
        //JSONUtils.prettyPrintJson(obj);
        
    }
    
}
