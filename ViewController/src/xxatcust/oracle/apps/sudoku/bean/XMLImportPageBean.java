package xxatcust.oracle.apps.sudoku.bean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.xml.bind.JAXBException;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.UploadedFile;

import xxatcust.oracle.apps.sudoku.model.module.SudokuAMImpl;
import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.ConfiguratorUtils;
import xxatcust.oracle.apps.sudoku.util.JSONUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.*;

public class XMLImportPageBean {
    List<ConfiguratorNodePOJO> allNodes;
    V93kQuote v93Obj;
    private RichOutputFormatted fileNameBinding;
    private RichOutputFormatted timestampBinding;
    private RichOutputFormatted uploadedByBinding;
    ChildPropertyTreeModel categoryTree;
    private ArrayList<NodeCategory> root;
    private RichPopup warningPopup;
    private RichOutputFormatted warnText;

    public XMLImportPageBean() {
        super();
    }
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(XMLImportPageBean.class);

    public void setAllNodes(List<ConfiguratorNodePOJO> allNodes) {
        this.allNodes = allNodes;
    }

    public List<ConfiguratorNodePOJO> getAllNodes() {
        if (allNodes == null) {
            Object parentObj = ADFUtils.getSessionScopeValue("parentObject");
            _logger.info("Print parentObj in List<ConfiguratorNodePOJO> " +
                         parentObj);
            if (parentObj != null) {
                v93Obj = (V93kQuote)parentObj;
                _logger.info("Print v93Obj in List<ConfiguratorNodePOJO> " +
                             v93Obj);
                allNodes = parseAllNodes(v93Obj);
                _logger.info("Print allNodes in List<ConfiguratorNodePOJO> " +
                             allNodes);
            }
        }
        return allNodes;
    }

    public void fileUploadVCE(ValueChangeEvent vce) {
        System.out.println("Inside Value Listener");
        //Whenever a new file is uploaded , Set the page flow object to null and allNodes to null
        if (vce.getNewValue() != null) {
            ADFUtils.setSessionScopeValue("parentObject", null);
            categoryTree = null;
            _logger.info("Print parentObject from session in fileUploadVCE " +
                         ADFUtils.getSessionScopeValue("parentObject"));
            allNodes = null;
            //Get File Object from VC Event
            UploadedFile fileVal = (UploadedFile)vce.getNewValue();
            fileNameBinding.setValue(fileVal.getFilename());
            uploadedByBinding.setValue(ADFContext.getCurrent().getSecurityContext().getUserName());
            timestampBinding.setValue(new Date());
            _logger.info("Print fileVal  fileUploadVCE" + fileVal);
            //Upload File to path- Return actual server path
            InputStream inputStream = uploadFile(fileVal);
            _logger.info("Print inputStream  fileUploadVCE" + inputStream);
            parseXMLToPojo(inputStream);
            //ResetUtils.reset(vce.getComponent());
        }
    }

    /**Method to upload file to actual path on Server*/
    private InputStream uploadFile(UploadedFile file) {
        UploadedFile myfile = file;
        InputStream inputStream = null;
        if (myfile == null) {

        } else {
            try {
                inputStream = myfile.getInputStream();
            } catch (IOException e) {
                ADFUtils.routeExceptions(e);
            }
            _logger.info("Print inputStream  uploadFile" + inputStream);
        }
        return inputStream;
    }

    private List<ConfiguratorNodePOJO> parseAllNodes(V93kQuote v93Obj) {
        List<ConfiguratorNodePOJO> nodeList =
            new ArrayList<ConfiguratorNodePOJO>();
        if (v93Obj != null) {
            //Get All ModelBom Nodes
            nodeList = v93Obj.getNodeCollection();
        }
        return nodeList;
    }

    public void parseXMLToPojo(InputStream inputStream) {
        try {
            V93kQuote parent = JaxbParser.jaxbXMLToObject(inputStream);
            _logger.info("Print parent  parseXMLToPojo" + parent);
            //Add Session details on the parent object
            SessionDetails sessionDetails = new SessionDetails();
            sessionDetails.setApplicationId("880");
            sessionDetails.setRespId("51156");
            sessionDetails.setUserId("11639");
            parent.setSessionDetails(sessionDetails);
            String jsonStr = JSONUtils.convertObjToJson(parent);
            V93kQuote obj = (V93kQuote)JSONUtils.convertJsonToObject(null);
            ADFUtils.setSessionScopeValue("parentObject", obj);
            //        _logger.info("Print jsonStr  parseXMLToPojo" +jsonStr);
            //        Object obj = null;
            //        //Reading JSOn from File to POJO
            //        ObjectMapper mapper = new ObjectMapper();
            //        _logger.info("Print mapper  parseXMLToPojo" +mapper);
            //        try {
            //            //comment this to run locally
            //            String responseJson =
            //                (String)ConfiguratorUtils.callConfiguratorServlet(jsonStr);
            //            _logger.info("Print responseJson  parseXMLToPojo" +responseJson);
            //           //String responseJson = (String)JSONUtils.convertJsonToObject(null);
            //            obj = mapper.readValue(responseJson, V93kQuote.class);
            //            _logger.info("Print obj  parseXMLToPojo" +obj);
            //            ADFUtils.setSessionScopeValue("parentObject", obj);
            //            _logger.info("Print parentObject from session in parseXMLToPojo " +ADFUtils.getSessionScopeValue("parentObject"));
            //        } catch (JsonParseException e)
            //        {   ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Exception in Parsing json:"+e.getOriginalMessage());
            //            e.printStackTrace();
            //            _logger.info("error Print JsonParseException  parseXMLToPojo" +e);
            //        } catch (JsonMappingException e)
            //        {
            //            _logger.info("error Print JsonMappingException  parseXMLToPojo" +e);
            //            e.printStackTrace();
            //
            //        } catch (IOException e) {
            //            _logger.info("error Print IOException  parseXMLToPojo" +e);
            //            e.printStackTrace();
            //
            //        }
        } catch (Exception ex) {
            ADFUtils.routeExceptions(ex);

        }

    }

    public void setFileNameBinding(RichOutputFormatted fileNameBinding) {
        this.fileNameBinding = fileNameBinding;
    }

    public RichOutputFormatted getFileNameBinding() {
        return fileNameBinding;
    }

    public void setTimestampBinding(RichOutputFormatted timestampBinding) {
        this.timestampBinding = timestampBinding;
    }

    public RichOutputFormatted getTimestampBinding() {
        return timestampBinding;
    }

    public void setUploadedByBinding(RichOutputFormatted uploadedByBinding) {
        this.uploadedByBinding = uploadedByBinding;
    }

    public RichOutputFormatted getUploadedByBinding() {
        return uploadedByBinding;
    }


    private List<String> removeDuplicatesFromList(List<String> inputList) {
        Set<String> set = new HashSet<String>(inputList);
        List<String> outputList = new ArrayList<String>();
        outputList.clear();
        outputList.addAll(set);
        return outputList;
    }


    public ChildPropertyTreeModel getCategoryTree() {
        // employeeTree =null ;
        try {
            if (categoryTree == null) {
                Object parentObj =
                    ADFUtils.getSessionScopeValue("parentObject");

                if (parentObj != null) {
                    V93kQuote obj = (V93kQuote)parentObj;
                    //Check if no exceptions from configurator
                    if (obj.getExceptionMap() != null &&
                        obj.getExceptionMap().getErrorMap() != null) {
                        HashMap<String, ArrayList<String>> exceptionMap =
                            obj.getExceptionMap().getErrorMap();
                        List<String> configErrors = exceptionMap.get("ERROR");
                        if (configErrors != null && configErrors.size() > 0) {
                            StringBuilder errMessage =
                                new StringBuilder("<html><body>");
                            for (String err : configErrors) {
                                errMessage.append("<p><b>" + err + "</b></p>");
                            }
                            errMessage.append("</body></html>");
                            throw new JboException(errMessage.toString());
                        }
                        //Check for warnings from configurator
                        List<String> configWarnings =
                            exceptionMap.get("NODE_MAPPING_WARN");
                        if (configWarnings != null &&
                            configWarnings.size() > 0) {
                            StringBuilder warningMessage =
                                new StringBuilder("<html><body>");
                            for (String warning : configWarnings) {
                                warningMessage.append("<p><b>" + warning +
                                                      "</b></p>");
                            }
                            warningMessage.append("</body></html>");
                            RichPopup.PopupHints hints =
                                new RichPopup.PopupHints();
                            warnText.setValue(warningMessage.toString());
                            warningPopup.show(hints);
                        }

                    }


                    List<String> catList = new ArrayList<String>();
                    List<String> distinctList = new ArrayList<String>();
                    List<ConfiguratorNodePOJO> allNodesList =
                        obj.getNodeCollection();
                    HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap =
                        new HashMap<String, List<ConfiguratorNodePOJO>>();
                    for (ConfiguratorNodePOJO node : allNodesList) {
                        catList.add(node.getNodeCategory());
                    }
                    distinctList = removeDuplicatesFromList(catList);

                    for (String distinctCategory : distinctList) {
                        List<ConfiguratorNodePOJO> temp =
                            new ArrayList<ConfiguratorNodePOJO>();
                        for (ConfiguratorNodePOJO node : allNodesList) {
                            if (distinctCategory != null &&
                                distinctCategory.equalsIgnoreCase(node.getNodeCategory())) {
                                temp.add(node);
                            }
                        }
                        allNodesByCategoriesMap.put(distinctCategory, temp);
                    }
                    root = new ArrayList<NodeCategory>();
                    Iterator it =
                        allNodesByCategoriesMap.entrySet().iterator();
                    NodeCategory firstLevel = null;
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        String category = (String)pair.getKey();
                        firstLevel =
                                new NodeCategory(category, null, null, null,
                                                 null, null, null);
                        root.add(firstLevel);
                        List<ConfiguratorNodePOJO> childList =
                            (List<ConfiguratorNodePOJO>)pair.getValue();
                        for (ConfiguratorNodePOJO node : childList) {
                            NodeCategory secondLevel =
                                new NodeCategory(category, node.getNodeName(),
                                                 node.getNodeDescription(),
                                                 node.getNodeQty(),
                                                 node.getNodeValue(),
                                                 node.getUnitPrice(),
                                                 node.getExtendedPrice());
                            firstLevel.addNodes(secondLevel);
                        }

                    }

                    categoryTree =
                            new ChildPropertyTreeModel(root, "childNodes");
                }

            }
        } catch (Exception e) {
            ADFUtils.routeExceptions(e);
        }
        return categoryTree;

    }

    public void controllerExceptionHandler() {
        ControllerContext context = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = context.getCurrentRootViewPort();
        Exception exceptionData = currentRootViewPort.getExceptionData();
        if (currentRootViewPort.isExceptionPresent()) {
            exceptionData.printStackTrace();
            currentRootViewPort.clearException();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                     exceptionData.getMessage(),
                                                     null));
        }
    }

    public void raiseException(ActionEvent actionEvent) {
        // Add event code here...
        JboException ex = new JboException("Custome Exveption");
        BindingContext bctx = BindingContext.getCurrent();
        ((DCBindingContainer)bctx.getCurrentBindingsEntry()).reportException(ex);
    }

    public void setWarningPopup(RichPopup warningPopup) {
        this.warningPopup = warningPopup;
    }

    public RichPopup getWarningPopup() {
        return warningPopup;
    }

    public void setWarnText(RichOutputFormatted warnText) {
        this.warnText = warnText;
    }

    public RichOutputFormatted getWarnText() {
        return warnText;
    }
}
