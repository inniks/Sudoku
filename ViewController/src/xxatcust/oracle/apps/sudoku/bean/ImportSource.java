package xxatcust.oracle.apps.sudoku.bean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.xml.bind.JAXBException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.commons.io.IOUtils;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;

import org.xml.sax.SAXException;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.ConfiguratorUtils;
import xxatcust.oracle.apps.sudoku.util.JSONUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.InputParams;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.SessionDetails;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class ImportSource {
    private String sourceSelected;
    private RichInputText budgetQuote;
    private RichInputText formalQuote;
    private RichInputFile inputFileBinding;

    public ImportSource() {
    }
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(XMLImportPageBean.class);
    private final String XSD_FILE =
        "xxatcust/oracle/apps/sudoku/view/V93000 C&Q 3.0 - XML File Schema.xsd";

    public void importSrcSelected(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            ADFUtils.setSessionScopeValue("inputParamsMap", new HashMap());
            String selectedVal = null;
            UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
            uiComp.processUpdates(FacesContext.getCurrentInstance());
            DCIteratorBinding iter =
                ADFUtils.findIterator("ImportSourceVO1Iterator");
            if (iter != null) {
                Row currRw = iter.getCurrentRow();
                if (currRw != null) {
                    currRw.setAttribute("BudgetQuoteId", null);
                    currRw.setAttribute("FormalQuoteId", null);
                    currRw.setAttribute("CopyRefConfig", null);
                    currRw.setAttribute("ReuseQuote", null);
                    inputFileBinding.setValue(null);
                }
            }
            BindingContainer bindings =
                (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            JUCtrlListBinding listBinding =
                (JUCtrlListBinding)bindings.get("ImportSrcMeaning");
            Row selectedRow = (Row)listBinding.getSelectedValue();
            if (selectedRow != null) {
                selectedVal =
                        (String)selectedRow.getAttribute("ImportSrcCode");
                setSourceSelected(selectedVal);
                System.out.println("Selected Import Source " + selectedVal);
            }
        }

    }

    public void copyRefChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        BindingContainer bindings =
            (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding listBinding =
            (JUCtrlListBinding)bindings.get("CopyRefConfig");
        Object selectedValue = listBinding.getSelectedValue();
    }

    public void reuseQuoteChange(ValueChangeEvent valueChangeEvent) {
        String selectedVal = null;
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        BindingContainer bindings =
            (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding listBinding =
            (JUCtrlListBinding)bindings.get("ReuseQuote");
        Row selectedRow = (Row)listBinding.getSelectedValue();
        if (selectedRow != null) {
            selectedVal = (String)selectedRow.getAttribute("ReuseCode");
        }
    }

    public void setSourceSelected(String sourceSelected) {
        this.sourceSelected = sourceSelected;
    }

    public String getSourceSelected() {
        return sourceSelected;
    }

    public void setBudgetQuote(RichInputText budgetQuote) {
        this.budgetQuote = budgetQuote;
    }

    public RichInputText getBudgetQuote() {
        return budgetQuote;
    }

    public void setFormalQuote(RichInputText formalQuote) {
        this.formalQuote = formalQuote;
    }

    public RichInputText getFormalQuote() {
        return formalQuote;
    }

    public void importConfiguration(ActionEvent actionEvent) {
        UIComponent uiComponent = (UIComponent)actionEvent.getSource();
        uiComponent.processUpdates(FacesContext.getCurrentInstance());
        ADFUtils.setSessionScopeValue("refreshImport", "Y");
    }

    public void dialogActionListener(ActionEvent actionEvent) {
        UIComponent uiComponent = (UIComponent)actionEvent.getSource();
        uiComponent.processUpdates(FacesContext.getCurrentInstance());
        //Take Values from VO binding
        String budgetQuoteNum = null;
        String formalQuoteNum = null;
        String importSource = null;
        String quoteNumber = null;
        Boolean reuseQuote =
            null; //true if "Re-use the existing Quote ID, if possible" is selected
        String copyReferenceConfiguration = null;
        boolean copyRefConf = false;
        DCIteratorBinding iter =
            ADFUtils.findIterator("ImportSourceVO1Iterator");
        if (iter != null) {
            Row currRow = iter.getCurrentRow();
            if (currRow != null) {
                importSource = (String)currRow.getAttribute("ImportSrcCode");
                budgetQuoteNum =
                        (String)currRow.getAttribute("BudgetQuoteId"); //can be formal quote id as well
                formalQuoteNum = (String)currRow.getAttribute("FormalQuoteId");
                // if(budgetQuoteNum!=null && b)
                reuseQuote = (Boolean)currRow.getAttribute("ReuseQuote");
                copyReferenceConfiguration =
                        (String)currRow.getAttribute("CopyRefConfig");
                if (copyReferenceConfiguration != null) {
                    if (copyReferenceConfiguration.equalsIgnoreCase("Y")) {
                        copyRefConf = true;
                    } else
                        copyRefConf = false;
                }
                if (importSource != null &&
                    importSource.equalsIgnoreCase("BUDGET_QUOTE")) {
                    quoteNumber = budgetQuoteNum;
                }
                if (importSource != null &&
                    importSource.equalsIgnoreCase("FORMAL_QUOTE")) {
                    quoteNumber = formalQuoteNum;
                }
                
                
                
                ADFContext af = ADFContext.getCurrent();
                HashMap inputParamsMap = new HashMap();
                inputParamsMap.put("importSource", importSource);
                inputParamsMap.put("quoteNumber", quoteNumber);
                inputParamsMap.put("reuseQuote", reuseQuote);
                inputParamsMap.put("copyRefConf", copyRefConf);
                ADFUtils.setSessionScopeValue("inputParamsMap",
                                              inputParamsMap);
                 
                af.getSessionScope().put("quoteNumber", quoteNumber);
            }
        }
        String str = null;
        UploadedFile xmlFile = (UploadedFile)inputFileBinding.getValue();
        // if (xmlFile != null) {

      
        ADFUtils.setSessionScopeValue("refreshImport", "Y");
        try {
            if (importSource != null &&
                importSource.equalsIgnoreCase("XML_FILE")) {
                parseXMLToPojo(xmlFile.getInputStream());
            } else {
                parseXMLToPojo(null);
            }
        } catch (Exception jaxbe) {
            str = jaxbe.getMessage();
            Throwable e = null;
            while (jaxbe.getCause() != null) {
                jaxbe = (Exception)jaxbe.getCause();

            }
            str = jaxbe.getMessage();
            //                ADFUtils.showFacesMessage("XML Validation failed :" + str,
            //                                          FacesMessage.SEVERITY_ERROR, null);
            // }
        }


        if (str == null) {
            RichPopup impSrcPopup =
                (RichPopup)ADFUtils.findComponentInRoot("imSrcP1");
            if (impSrcPopup != null) {
                impSrcPopup.cancel();
            }
        }
        RequestContext.getCurrentInstance().addPartialTarget(ADFUtils.findComponentInRoot("ps1imXML"));

    }

    public void setInputFileBinding(RichInputFile inputFileBinding) {
        this.inputFileBinding = inputFileBinding;
    }

    public RichInputFile getInputFileBinding() {
        return inputFileBinding;
    }

    public void parseXMLToPojo(InputStream inputStream) throws IOException,
                                                               JsonGenerationException,
                                                               JsonMappingException,
                                                               JAXBException,
                                                               SAXException {
        String importSource = null;
        Object obj = null;
        SessionDetails sessionDetails = new SessionDetails();
        InputParams inputParam = new InputParams();
        //Get Session details added to the POJO object
        //sessionDetails.setApplicationId((String)ADFUtils.getSessionScopeValue("ApplId"));
        //sessionDetails.setRespId((String)ADFUtils.getSessionScopeValue("RespId"));
        //sessionDetails.setUserId((String)ADFUtils.getSessionScopeValue("UserId"));
        sessionDetails.setApplicationId("880");
        sessionDetails.setRespId("51156");
        sessionDetails.setUserId("11639");
        //Add Input parameters to POJO
        HashMap inputParamsMap =
            (HashMap)ADFUtils.getSessionScopeValue("inputParamsMap");
        if (inputParamsMap != null && !inputParamsMap.isEmpty()) {

            if (inputParamsMap.get("importSource") != null) {
                importSource = (String)inputParamsMap.get("importSource");
                inputParam.setImportSource((String)inputParamsMap.get("importSource"));
            }
            if (inputParamsMap.get("quoteNumber") != null) {
                inputParam.setQuoteNumber((String)inputParamsMap.get("quoteNumber"));
            }
            if (inputParamsMap.get("reuseQuote") != null) {
                inputParam.setReuseQuote((Boolean)inputParamsMap.get("reuseQuote"));
            }
            if (inputParamsMap.get("copyRefConf") != null) {
                inputParam.setCopyReferenceConfiguration((Boolean)inputParamsMap.get("copyRefConf"));
            }
        }
        //Check what is the import source
        if (importSource != null &&
            importSource.equalsIgnoreCase("XML_FILE")) {
            System.out.println("Input Stream is " + inputStream);
            File xsdFile = readXsdResource();
            V93kQuote parent = null;
            parent = JaxbParser.jaxbXMLToObject(inputStream, xsdFile);
            //Add session and input params
            parent.setSessionDetails(sessionDetails);
            parent.setInputParams(inputParam);
            _logger.info("Print parent  parseXMLToPojo" + parent);

            String jsonStr = JSONUtils.convertObjToJson(parent);
            //V93kQuote obj = (V93kQuote)JSONUtils.convertJsonToObject(null);
            //ADFUtils.setSessionScopeValue("parentObject", obj);
            _logger.info("Print jsonStr  parseXMLToPojo" + jsonStr);

            //Reading JSOn from File to POJO
            ObjectMapper mapper = new ObjectMapper();
            _logger.info("Print mapper  parseXMLToPojo" + mapper);
            //comment this to run locally
            System.out.println("Input JSON " + jsonStr);
            String responseJson =
                (String)ConfiguratorUtils.callConfiguratorServlet(jsonStr);
            System.out.println("Response JSON " + responseJson);
            _logger.info("Print responseJson  parseXMLToPojo" + responseJson);
            //String responseJson = (String)JSONUtils.convertJsonToObject(null);
            obj = mapper.readValue(responseJson, V93kQuote.class);
        } else if (importSource != null) {
            V93kQuote v93k = new V93kQuote();
            v93k.setInputParams(inputParam);
            v93k.setSessionDetails(sessionDetails);
            obj = v93k;
            String jsonStr = JSONUtils.convertObjToJson(obj);
            //V93kQuote obj = (V93kQuote)JSONUtils.convertJsonToObject(null);
            //ADFUtils.setSessionScopeValue("parentObject", obj);
            _logger.info("Print jsonStr  parseXMLToPojo" + jsonStr);

            //Reading JSOn from File to POJO
            ObjectMapper mapper = new ObjectMapper();
            _logger.info("Print mapper  parseXMLToPojo" + mapper);
            //comment this to run locally
            System.out.println("Input JSON " + jsonStr);
            String responseJson =
                (String)ConfiguratorUtils.callConfiguratorServlet(jsonStr);
            System.out.println("Response JSON " + responseJson);
            _logger.info("Print responseJson  parseXMLToPojo" + responseJson);
            //String responseJson = (String)JSONUtils.convertJsonToObject(null);
            obj = mapper.readValue(responseJson, V93kQuote.class);

        }
        ADFUtils.setSessionScopeValue("parentObject", obj);
        _logger.info("Print parentObject from session in parseXMLToPojo " +
                     ADFUtils.getSessionScopeValue("parentObject"));


    }

    public File readXsdResource() {
        File f = null;
        InputStream asStream =
            this.getClass().getClassLoader().getResourceAsStream(XSD_FILE);
        if (asStream == null) {
            // file not foun
            System.out.println("File Not found");
            _logger.info("File not found: '" + XSD_FILE + "'");
        } else {
            System.out.println("Stream found");

            try {
                f = stream2file(asStream, "V93", ".xsd");
                System.out.println("File Name " + f.getName());
                return f;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";

    public File stream2file(InputStream in, String PREFIX,
                            String SUFFIX) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
        } catch (Exception e) {
            ADFUtils.routeExceptions(e);
        }
        return tempFile;
    }


    public void cancelPopup(ActionEvent actionEvent) {
        RichPopup impSrcPopup =
            (RichPopup)ADFUtils.findComponentInRoot("imSrcP1");
        if (impSrcPopup != null) {
            //impSrcPopup.hide();
            impSrcPopup.cancel();
        }
    }

    public void openQuote(ActionEvent actionEvent) {
        RichPopup impSrcPopup =
            (RichPopup)ADFUtils.findComponentInRoot("imSrcP1");
        if (impSrcPopup != null) {
            //impSrcPopup.hide();
            impSrcPopup.cancel();
        }
        RichPanelGroupLayout pgl = (RichPanelGroupLayout)ADFUtils.findComponentInRoot("dashPGL");
        RequestContext.getCurrentInstance().addPartialTarget(pgl);
    }

    public void newFileUploaded(ValueChangeEvent valueChangeEvent) {
        
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        System.out.println("Inside new File Uploaded");
        //dialogActionListener(null);
    }
    
    public String getRefreshToken(){
        return String.valueOf(System.currentTimeMillis()) ;
    }
}
