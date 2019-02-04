package xxatcust.oracle.apps.sudoku.bean;

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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.UploadedFile;

import xxatcust.oracle.apps.sudoku.model.module.SudokuAMImpl;
import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.ConfiguratorUtils;
import xxatcust.oracle.apps.sudoku.util.JSONUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.*;

public class XMLImportPageBean {
    List<PogoMappingFile> allPmf;
    List<ConfiguratorNodePOJO> allNodes;
    private RichTable pmfTable;
    V93kQuote v93Obj;
    private RichOutputFormatted fileNameBinding;
    private RichOutputFormatted timestampBinding;
    private RichOutputFormatted uploadedByBinding;

    public XMLImportPageBean() {
        super();
    }
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(XMLImportPageBean.class);

    public void setAllPmf(List<PogoMappingFile> allPmf) {

        this.allPmf = allPmf;
    }

    public List<PogoMappingFile> getAllPmf() {
        if (allPmf == null) {
            Object parentObj = ADFUtils.getPageFlowScopeValue("parentObject");
            _logger.info("Print parentObj in List<PogoMappingFile> " +
                         parentObj);
            if (parentObj != null) {
                v93Obj = (V93kQuote)parentObj;
                _logger.info("Print v93Obj in List<PogoMappingFile>" + v93Obj);
                allPmf = v93Obj.getConfigObject().getPmfObject().getPmfMap();
                _logger.info("Print allPmf in List<PogoMappingFile> " +
                             allPmf);
            }
        }
        return allPmf;
        //System.out.println(allPmf);

    }

    public void setPmfTable(RichTable pmfTable) {
        this.pmfTable = pmfTable;
    }

    public RichTable getPmfTable() {
        return pmfTable;
    }

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
                _logger.info("Print inputStream  uploadFile" + inputStream);
            } catch (Exception ex) {
                // handle exception
                ex.printStackTrace();
                //  _logger.("error in uploadFile" +ex);
                _logger.info("error Exception in uploadFile" + ex);

            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    _logger.info("error in IOException uploadFile" + e);
                }
            }

        }
        //Returns the path where file is stored
        return inputStream;
    }

    private List<ConfiguratorNodePOJO> parseAllNodes(V93kQuote v93Obj) {
        List<ConfiguratorNodePOJO> nodeList =
            new ArrayList<ConfiguratorNodePOJO>();

        if (v93Obj != null) {
            //Get All ModelBom Nodes
            List<ConfiguratorNodePOJO> infraUpgradeNodes =
                null, swLicensesNodes = null, wtySupportNodes =
                null, calDiagNodes = null, rfNodes = null, digitalNodes =
                null, msNodes = null, dpsNodes = null, dockingNodes =
                null, miscNodes = null, coolingNodes = null, dufifUtilNodes =
                null, maniNodes = null, theadNodes = null, wkstaNodes = null;
            if (v93Obj.getConfigObject().getModelbomObject().getInfraUpgradeObject() !=
                null) {
                infraUpgradeNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraUpgradeObject().getInfraUpgradeItems();
                _logger.info("Print infraUpgradeNodes  List<ConfiguratorNodePOJO>" +
                             infraUpgradeNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getSwlicensesObject() !=
                null) {
                swLicensesNodes =
                        v93Obj.getConfigObject().getModelbomObject().getSwlicensesObject().getSwlicenseItems();
                _logger.info("Print swLicensesNodes  List<ConfiguratorNodePOJO>" +
                             swLicensesNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getWtysupportObject() !=
                null) {
                wtySupportNodes =
                        v93Obj.getConfigObject().getModelbomObject().getWtysupportObject().getAllWtySupportItems();
                _logger.info("Print wtySupportNodes  List<ConfiguratorNodePOJO>" +
                             wtySupportNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getCaldiag() !=
                null) {
                calDiagNodes =
                        v93Obj.getConfigObject().getModelbomObject().getCaldiag().getCalDiagItems();
                _logger.info("Print calDiagNodes  List<ConfiguratorNodePOJO>" +
                             calDiagNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getRf() != null) {
                rfNodes =
                        v93Obj.getConfigObject().getModelbomObject().getRf().getAllRf();
                _logger.info("Print rfNodes  List<ConfiguratorNodePOJO>" +
                             rfNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDigitalObject() !=
                null) {
                digitalNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDigitalObject().getDigitalItems();
                _logger.info("Print digitalNodes  List<ConfiguratorNodePOJO>" +
                             digitalNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getMs() != null) {
                msNodes =
                        v93Obj.getConfigObject().getModelbomObject().getMs().getAllMs();
                _logger.info("Print msNodes  List<ConfiguratorNodePOJO>" +
                             msNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDpsObject() !=
                null) {
                dpsNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDpsObject().getDpsItems();
                _logger.info("Print dpsNodes  List<ConfiguratorNodePOJO>" +
                             dpsNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDockingObject() !=
                null) {
                dockingNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDockingObject().getAllDockingItems();
                _logger.info("Print dockingNodes  List<ConfiguratorNodePOJO>" +
                             dockingNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject() != null) {
                miscNodes =
                        v93Obj.getConfigObject().getModelbomObject().getMisc().getMiscItems();
                _logger.info("Print miscNodes  List<ConfiguratorNodePOJO>" +
                             miscNodes);
            }
            //Inside ModelBom-->Infra
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getCoolingObject() !=
                null) {
                coolingNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getCoolingObject().getCoolingItems();
                _logger.info("Print coolingNodes  List<ConfiguratorNodePOJO>" +
                             coolingNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getDutifutilObject() !=
                null) {
                dufifUtilNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getDutifutilObject().getDutifUtilItems();

                _logger.info("Print dufifUtilNodes  List<ConfiguratorNodePOJO>" +
                             dufifUtilNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getManiObject() !=
                null) {
                maniNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getManiObject().getAllManiItems();
                _logger.info("Print maniNodes  List<ConfiguratorNodePOJO>" +
                             maniNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getTheadObject() !=
                null) {
                List<ConfiguratorNodePOJO> theaditems =
                    v93Obj.getConfigObject().getModelbomObject().getInfraObject().getTheadObject().getTheadItems();
                for (ConfiguratorNodePOJO o : theaditems) {
                    o.setNodeCategory("THEAD CATEGORY");
                }
                theadNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getTheadObject().getTheadItems();
                _logger.info("Print theadNodes  List<ConfiguratorNodePOJO>" +
                             theadNodes);
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getWkstaObject() !=
                null) {
                wkstaNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getWkstaObject().getWkstaItems();

                _logger.info("Print wkstaNodes  List<ConfiguratorNodePOJO>" +
                             wkstaNodes);
            }

            if (infraUpgradeNodes != null && infraUpgradeNodes.size() > 0) {
                nodeList.addAll(infraUpgradeNodes);
            }
            if (swLicensesNodes != null && swLicensesNodes.size() > 0) {
                nodeList.addAll(swLicensesNodes);
            }
            if (wtySupportNodes != null && wtySupportNodes.size() > 0) {
                nodeList.addAll(wtySupportNodes);
            }
            if (calDiagNodes != null && calDiagNodes.size() > 0) {
                nodeList.addAll(calDiagNodes);
            }
            if (rfNodes != null && rfNodes.size() > 0) {
                nodeList.addAll(rfNodes);
            }
            if (digitalNodes != null && digitalNodes.size() > 0) {
                nodeList.addAll(digitalNodes);
            }
            if (msNodes != null && msNodes.size() > 0) {
                nodeList.addAll(msNodes);
            }
            if (dpsNodes != null && dpsNodes.size() > 0) {
                nodeList.addAll(dpsNodes);
            }
            if (dockingNodes != null && dockingNodes.size() > 0) {
                nodeList.addAll(dockingNodes);
            }
            if (miscNodes != null && miscNodes.size() > 0) {
                nodeList.addAll(miscNodes);
            }

            if (coolingNodes != null && coolingNodes.size() > 0) {
                nodeList.addAll(coolingNodes);
            }
            if (dufifUtilNodes != null && dufifUtilNodes.size() > 0) {
                nodeList.addAll(dufifUtilNodes);
            }
            if (maniNodes != null && maniNodes.size() > 0) {
                nodeList.addAll(maniNodes);
            }
            if (theadNodes != null && theadNodes.size() > 0) {
                nodeList.addAll(theadNodes);
            }
            if (wkstaNodes != null && wkstaNodes.size() > 0) {
                nodeList.addAll(wkstaNodes);
            }
        }
        //mapCategoriesToNodes(allNodes);
        return nodeList;
    }

    public void parseXMLToPojo(InputStream inputStream) {
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
    //
    //    public List<HashMap> executeCategoryVO(){
    //        OperationBinding op = ADFUtils.findOperation("getNodeCategoryList");
    //        if(op!=null){
    //           List<String> nodeCatList = (List<String>)op.execute();
    //           System.out.println(nodeCatList);
    //           if(nodeCatList!=null && nodeCatList.size()>0){
    //               ADFUtils.setSessionScopeValue("nodeCategoryList", nodeCatList);
    //           }
    //        }
    //    }
    //

    public Boolean get(Object key) {
        String attrName = (String)key;
        boolean isSame = false;
        // get the currently processed row, using row expression #{row}
        JUCtrlHierNodeBinding row =
            (JUCtrlHierNodeBinding)ADFUtils.resolveExpression("#{row}");
        JUCtrlHierBinding tableBinding = row.getHierBinding();
        int rowRangeIndex = row.getViewObject().getRangeIndexOf(row.getRow());
        Object currentAttrValue = row.getRow().getAttribute(attrName);
        if (rowRangeIndex > 0) {
            Object previousAttrValue =
                tableBinding.getAttributeFromRow(rowRangeIndex - 1, attrName);
            isSame =
                    currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
        } else if (tableBinding.getRangeStart() > 0) {
            // previous row is in previous range, we create separate rowset iterator,
            // so we can change the range start without messing up the table rendering which uses
            // the default rowset iterator
            int absoluteIndexPreviousRow = tableBinding.getRangeStart() - 1;
            RowSetIterator rsi = null;
            try {
                rsi =
tableBinding.getViewObject().getRowSet().createRowSetIterator(null);
                rsi.setRangeStart(absoluteIndexPreviousRow);
                Row previousRow = rsi.getRowAtRangeIndex(0);
                Object previousAttrValue = previousRow.getAttribute(attrName);
                isSame =
                        currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
            } finally {
                rsi.closeRowSetIterator();
            }
        }
        System.out.println("Is SAME: " + isSame);
        return isSame;
    }

    private List<ConfiguratorNodePOJO> mapCategoriesToNodes(List<ConfiguratorNodePOJO> allNodes) {
        OperationBinding op = ADFUtils.findOperation("getNodeCategoryMap");
        Hashtable<String, String> nodeCatMap = new Hashtable<String, String>();
        if (op != null) {
            nodeCatMap = (Hashtable<String, String>)op.execute();
        }
        if (allNodes!=null && allNodes.size()>0) {
            for (Iterator<ConfiguratorNodePOJO> iter = allNodes.iterator();
                 iter.hasNext(); ) {
                ConfiguratorNodePOJO element = iter.next();
                if (nodeCatMap != null &&
                    nodeCatMap.get(element.getNodeName()) != null) {
                    element.setNodeCategory(nodeCatMap.get(element.getNodeName()));
                }
            }
        }
        return allNodes;
    }

}
