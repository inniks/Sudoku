package xxatcust.oracle.apps.sudoku.bean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.ServletContext;

import javax.xml.bind.JAXBException;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;

import xxatcust.oracle.apps.sudoku.model.module.SudokuAMImpl;
import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.ConfigNodeComparator;
import xxatcust.oracle.apps.sudoku.util.ConfiguratorUtils;
import xxatcust.oracle.apps.sudoku.util.JSONUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.util.NodeComparator;
import xxatcust.oracle.apps.sudoku.util.XMLUtils;
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
    private boolean showListHeader = false;
    private RichOutputFormatted dubugMsgBinding;
    private RichOutputFormatted debugMsgBind;
    private Boolean productsRendered = true;
    private Boolean spaceRendered = false;
    private final String PROPERTY_FILE =
        "xxatcust/oracle/apps/sudoku/view/V93000 C&Q 3.0 - XML File Schema.xsd";
    private Properties mViewProperties = null;
    private RichOutputFormatted modelName;
    private RichInputFile uploadFileBinding;
    private RichOutputText modelNameBind;
    private RichOutputText modelDescBind;
    private RichOutputText modelQtyBind;
    private RichOutputText modelPriceBind;
    private RichOutputText extendedPriceBind;

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
        UploadedFile fileVal =
            (UploadedFile)uploadFileBinding.getValue(); //(UploadedFile)vce.getNewValue();

        InputStream inputStream = uploadFile(fileVal);
        ADFUtils.clearControllerException();
        ADFUtils.setSessionScopeValue("parentObject", null);
        categoryTree = null;
        _logger.info("Print parentObject from session in fileUploadVCE " +
                     ADFUtils.getSessionScopeValue("parentObject"));
        allNodes = null;
        fileNameBinding.setValue(fileVal.getFilename());
        uploadedByBinding.setValue(ADFContext.getCurrent().getSessionScope().get("UserName"));
        timestampBinding.setValue(new Date());
        _logger.info("Print fileVal  fileUploadVCE" + fileVal);
        _logger.info("Print inputStream  fileUploadVCE" + inputStream);
        parseXMLToPojo(inputStream);
        showListHeader = true;
        //ResetUtils.reset(vce.getComponent());
    }

    public void parseXMLToPojo(InputStream inputStream) {
        try {
            System.out.println("Input Stream is "+inputStream);
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
            //            _logger.info("Print jsonStr  parseXMLToPojo" + jsonStr);
            //            Object obj = null;
            //            //Reading JSOn from File to POJO
            //            ObjectMapper mapper = new ObjectMapper();
            //            _logger.info("Print mapper  parseXMLToPojo" + mapper);
            //            try {
            //                //comment this to run locally
            //                String responseJson =
            //                    (String)ConfiguratorUtils.callConfiguratorServlet(jsonStr);
            //                _logger.info("Print responseJson  parseXMLToPojo" +
            //                             responseJson);
            //                //String responseJson = (String)JSONUtils.convertJsonToObject(null);
            //                obj = mapper.readValue(responseJson, V93kQuote.class);
            //                _logger.info("Print obj  parseXMLToPojo" + obj);
            //                ADFUtils.setSessionScopeValue("parentObject", obj);
            //                _logger.info("Print parentObject from session in parseXMLToPojo " +
            //                             ADFUtils.getSessionScopeValue("parentObject"));
            //            } catch (JsonParseException e) {
            //                ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR,
            //                                    "Exception in Parsing json:" +
            //                                    e.getOriginalMessage());
            //                e.printStackTrace();
            //                _logger.info("error Print JsonParseException  parseXMLToPojo" +
            //                             e);
            //            } catch (JsonMappingException e) {
            //                _logger.info("error Print JsonMappingException  parseXMLToPojo" +
            //                             e);
            //                e.printStackTrace();
            //
            //            } catch (IOException e) {
            //                _logger.info("error Print IOException  parseXMLToPojo" + e);
            //                e.printStackTrace();
            //
            //            }
        } catch (Exception ex) {
            ADFUtils.routeExceptions(ex);

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
                //File xmlFile = stream2file(inputStream, "FileX", ".xml");
                //File xsdFile = readXsdResource();
//                String validated =
//                    XMLUtils.validateXMLSchema(xsdFile, xmlFile);
            } catch (IOException e) {
                ADFUtils.routeExceptions(e);
            }
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
        try {
            String refreshImport =
                (String)ADFUtils.getSessionScopeValue("refreshImport");
            if (categoryTree == null && refreshImport != null &&
                refreshImport.equalsIgnoreCase("Y")) {
                //resetAllBindings();
                Object parentObj =
                    ADFUtils.getSessionScopeValue("parentObject");
                ADFUtils.setSessionScopeValue("refreshImport", null);
                if (parentObj != null) {
                    V93kQuote obj = (V93kQuote)parentObj;
                    //Check if no exceptions from configurator
                    if (obj.getExceptionMap() != null) {
                        TreeMap<String, ArrayList<String>> exceptionMap =
                            obj.getExceptionMap().getErrorList();
                        TreeMap<String, ArrayList<String>> notifications =
                            obj.getExceptionMap().getNotificationList();
                        TreeMap<String, ArrayList<String>> warnings =
                            obj.getExceptionMap().getWarningList();
                        TreeMap<String, ArrayList<String>> debugList =
                            obj.getExceptionMap().getDebugMessageList();
                        List<String> debugMessages =
                            obj.getExceptionMap().getDebugMessages();
                        List<String> errorMessages =
                            obj.getExceptionMap().getErrorsMessages();
                        StringBuilder errMessage =
                            new StringBuilder("<html><body>");
                        if (exceptionMap != null && exceptionMap.size() > 0) {

                            for (Map.Entry<String, ArrayList<String>> entry :
                                 exceptionMap.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<String> value = entry.getValue();
                                for (String str : value) {
                                    errMessage.append("<p><b>" + str +
                                                      "</b></p>");
                                }
                            }

                            errMessage.append("</body></html>");

                        }
                        if (errorMessages != null &&
                            errorMessages.size() > 0) {
                            for (String str : errorMessages) {
                                errMessage.append("<p><b>" + str + "</b></p>");
                            }
                            errMessage.append("</body></html>");
                        }
                        if (errMessage != null &&
                            !errMessage.toString().equalsIgnoreCase("<html><body>")) {
                            throw new JboException(errMessage.toString());
                        }
                        //Check for warnings from configurator
                        StringBuilder warningMessage =
                            new StringBuilder("<html><body>");
                        if (warnings != null && warnings.size() > 0) {


                            for (Map.Entry<String, ArrayList<String>> entry :
                                 warnings.entrySet()) {
                                String key = entry.getKey();
                                //iterate for each key
                                warningMessage.append("<p><b>" + key + " : " +
                                                      "</b></p>");
                                ArrayList<String> value = entry.getValue();
                                for (String str : value) {
                                    warningMessage.append("<p><b>" + str +
                                                          "</b></p>");
                                }
                            }
                            warningMessage.append("</body></html>");
                        }
                        //Check for notification messages from configurator

                        if (notifications != null &&
                            notifications.size() > 0) {
                            for (Map.Entry<String, ArrayList<String>> entry :
                                 notifications.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<String> value = entry.getValue();
                                warningMessage.append("<p><b>" + key + " : " +
                                                      "</b></p>");
                                for (String str : value) {
                                    warningMessage.append("<p><b>" + str +
                                                          "</b></p>");
                                }
                            }
                            warningMessage.append("</body></html>");

                            // debugMsgBind.setValue(debugStr.toString());
                        }
                        warnText.setValue(warningMessage.toString());
                        RichPopup.PopupHints hints =
                            new RichPopup.PopupHints();
                        if (warningMessage != null &&
                            !warningMessage.toString().equalsIgnoreCase("<html><body>")) {
                            System.out.println("Warning Message " +
                                               warningMessage.toString());
                            warningPopup.show(hints);
                        }
                        StringBuilder debugMessage =
                            new StringBuilder("<html><body>");
                        if (debugList != null && debugList.size() > 0) {
                            for (Map.Entry<String, ArrayList<String>> entry :
                                 debugList.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<String> value = entry.getValue();
                                debugMessage.append("<p><b>" + key + " : " +
                                                    "</b></p>");
                                for (String str : value) {
                                    debugMessage.append("<p><b>" + str +
                                                        "</b></p>");
                                }
                            }
                            debugMessage.append("</body></html>");
                        }
                        if (debugMessages != null &&
                            debugMessages.size() > 0) {
                            for (String str : debugMessages) {
                                debugMessage.append("<p><b>" + str +
                                                    "</b></p>");
                            }
                        }
                        debugMsgBind.setValue(debugMessage.toString());
                    }

                    if (obj != null && obj.getSessionDetails() != null &&
                        obj.getSessionDetails().getModelName() != null) {
                        //modelName.setValue(obj.getSessionDetails().getModelName());
                        System.out.println("Model Name is " + modelName);
                    }

                    List<String> catList = new ArrayList<String>();
                    List<String> distinctList = new ArrayList<String>();
                    List<ConfiguratorNodePOJO> allNodesList =
                        obj.getNodeCollection();
                    HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap =
                        new HashMap<String, List<ConfiguratorNodePOJO>>();
                    for (ConfiguratorNodePOJO node : allNodesList) {
                        if (node.getNodeCategory() != null &&
                            node.getPrintGroupLevel() != null) {
                            catList.add(node.getNodeCategory() + "-" +
                                        (node.getPrintGroupLevel() != null ?
                                         node.getPrintGroupLevel() : "0"));
                        }
                    }
                    distinctList = removeDuplicatesFromList(catList);
                    for (String distinctCategory : distinctList) {
                        List<ConfiguratorNodePOJO> temp =
                            new ArrayList<ConfiguratorNodePOJO>();
                        for (ConfiguratorNodePOJO node : allNodesList) {
                            if (distinctCategory != null &&
                                distinctCategory.equalsIgnoreCase(node.getNodeCategory() +
                                                                  "-" +
                                                                  node.getPrintGroupLevel())) {
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
                        String Key = (String)pair.getKey();
                        System.out.println("Key " + Key);
                        String[] arr = Key.split("-");
                        String category = arr[0];
                        String printGrpLevel = arr[1];
                        firstLevel =
                                new NodeCategory(category, null, null, null,
                                                 null, null, null, null,
                                                 printGrpLevel);
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
                                                 node.getExtendedPrice(),
                                                 node.getNodeColor(),
                                                 node.getPrintGroupLevel());
                            firstLevel.addNodes(secondLevel);
                        }

                    }
                    //Trying to sort root
                    for (NodeCategory nc : root) {
                        System.out.println(nc.getCategory() + " " +
                                           nc.getPrintGroupLevel());
                    }
                    NodeComparator comparator = new NodeComparator();
                    Collections.sort(root, comparator);

                    for (NodeCategory nc : root) {
                        System.out.println(nc.getCategory() + " " +
                                           nc.getPrintGroupLevel());
                    }
                    categoryTree =
                            new ChildPropertyTreeModel(root, "childNodes");

                }

            }
        } catch (Exception e) {

            ADFUtils.routeExceptions(e);
        } finally {
            //cleanup
            ADFUtils.setSessionScopeValue("refreshImport", null);
            ADFUtils.setSessionScopeValue("parentObject", null);
            //categoryTree = null ;
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

    public void setShowListHeader(boolean showListHeader) {
        this.showListHeader = showListHeader;
    }

    public boolean isShowListHeader() {
        return showListHeader;
    }

    public void listViewRowSelection(SelectionEvent selectionEvent) {
        CollectionModel treeModel = null;
        RichListView listView = (RichListView)selectionEvent.getSource();
        treeModel = (CollectionModel)listView.getValue();
        RowKeySet selectedChildKeys = listView.getSelectedRowKeys();
        if (!selectedChildKeys.isEmpty()) {
            List<NodeCategory> nodeList =
                (List<NodeCategory>)treeModel.getWrappedData();
            Iterator selectedCharIter = selectedChildKeys.iterator();
            while (selectedCharIter.hasNext()) {
                List val = (List)selectedCharIter.next();
                NodeCategory nc =
                    nodeList.get(Integer.parseInt(val.get(0).toString()));
                List childNodes = nc.getChildNodes();
                if (val.size() > 0) {
                    NodeCategory nc1 =
                        (NodeCategory)childNodes.get(Integer.parseInt(val.get(1).toString()));
                    System.out.println(nc1.getNodeName());
                    ADFUtils.showFacesMessage("Row Selected:: " +
                                              nc1.getNodeName() + " " +
                                              nc1.getNodeDescription(),
                                              FacesMessage.SEVERITY_INFO);
                }
            }
        }
    }

    public void setDubugMsgBinding(RichOutputFormatted dubugMsgBinding) {
        this.dubugMsgBinding = dubugMsgBinding;
    }

    public RichOutputFormatted getDubugMsgBinding() {
        return dubugMsgBinding;
    }

    public void setDebugMsgBind(RichOutputFormatted debugMsgBind) {
        this.debugMsgBind = debugMsgBind;
    }

    public RichOutputFormatted getDebugMsgBind() {
        return debugMsgBind;
    }

    public void setProductsRendered(Boolean productsRendered) {
        this.productsRendered = productsRendered;
    }

    public Boolean getProductsRendered() {
        return productsRendered;
    }


    public void hideProducts(ActionEvent actionEvent) {
        if (productsRendered) {
            setProductsRendered(false);
        }
        setSpaceRendered(true);
    }

    public void showProducts(ActionEvent actionEvent) {
        if (!productsRendered) {
            setProductsRendered(true);
        }
        setSpaceRendered(false);
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

    public void setModeName(RichOutputFormatted modelName) {
        this.modelName = modelName;
    }

    public RichOutputFormatted getModeName() {
        return modelName;
    }

    public void importConfiguration(ActionEvent actionEvent) {
        UIComponent uiComponent = (UIComponent)actionEvent.getSource();
        uiComponent.processUpdates(FacesContext.getCurrentInstance());
        ADFUtils.setSessionScopeValue("refreshImport", "Y");
        System.out.println("Button Pressed");
        //Trying to clear previous values here
        warnText.setValue(null);
        showListHeader = false;
        debugMsgBind.setValue(null);
        productsRendered = true;
        spaceRendered = false;
        fileUploadVCE(null);
    }


    public void setUploadFileBinding(RichInputFile uploadFileBinding) {
        this.uploadFileBinding = uploadFileBinding;
    }

    public RichInputFile getUploadFileBinding() {
        return uploadFileBinding;
    }

    public void setSpaceRendered(Boolean spaceRendered) {
        this.spaceRendered = spaceRendered;
    }

    public Boolean getSpaceRendered() {
        return spaceRendered;
    }


    public void setModelNameBind(RichOutputText modelNameBind) {
        this.modelNameBind = modelNameBind;
    }

    public RichOutputText getModelNameBind() {
        return modelNameBind;
    }

    public void setModelDescBind(RichOutputText modelDescBind) {
        this.modelDescBind = modelDescBind;
    }

    public RichOutputText getModelDescBind() {
        return modelDescBind;
    }

    public void setModelQtyBind(RichOutputText modelQtyBind) {
        this.modelQtyBind = modelQtyBind;
    }

    public RichOutputText getModelQtyBind() {
        return modelQtyBind;
    }

    public void setModelPriceBind(RichOutputText modelPriceBind) {
        this.modelPriceBind = modelPriceBind;
    }

    public RichOutputText getModelPriceBind() {
        return modelPriceBind;
    }

    public void setExtendedPriceBind(RichOutputText extendedPriceBind) {
        this.extendedPriceBind = extendedPriceBind;
    }

    public RichOutputText getExtendedPriceBind() {
        return extendedPriceBind;
    }

    private void resetAllBindings() {
        //        ResetUtils.reset(fileNameBinding);
        //        ResetUtils.reset(timestampBinding);
        //        ResetUtils.reset(uploadedByBinding);
        //        ResetUtils.reset(warnText);
        //        ResetUtils.reset(dubugMsgBinding);
        //        ResetUtils.reset(debugMsgBind);
        //        ResetUtils.reset(modelName);
        //        ResetUtils.reset(uploadFileBinding);
        //        ResetUtils.reset(modelNameBind);
        //        ResetUtils.reset(modelDescBind);
        //        ResetUtils.reset(modelQtyBind);
        //        ResetUtils.reset(modelPriceBind);
        //        ResetUtils.reset(extendedPriceBind);
        //        showListHeader = false;
        //        productsRendered = true;
        //        spaceRendered = false;
    }


    public void readRes(ActionEvent actionEvent) {
        InputStream asStream =
            this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
        if (asStream == null) {
            // file not foun
            System.out.println("File Not found");
            _logger.info("File not found: '" + PROPERTY_FILE + "'");
            return;
        } else {
            System.out.println("Stream found");
            File f;
            try {
                f = stream2file(asStream, "V93", ".xsd");
                System.out.println("File Name " + f.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File readXsdResource() {
        File f = null;
        InputStream asStream =
            this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);
        if (asStream == null) {
            // file not foun
            System.out.println("File Not found");
            _logger.info("File not found: '" + PROPERTY_FILE + "'");
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
    
    
}
