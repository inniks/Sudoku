package xxatcust.oracle.apps.sudoku.bean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.util.ResetUtils;

import org.apache.myfaces.trinidad.model.UploadedFile;

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

    public XMLImportPageBean() {
        super();
    }

    public void setAllPmf(List<PogoMappingFile> allPmf) {

        this.allPmf = allPmf;
    }

    public List<PogoMappingFile> getAllPmf() {
        if (allPmf == null) {
            Object parentObj = ADFUtils.getPageFlowScopeValue("parentObject");
            if (parentObj != null) {
                v93Obj = (V93kQuote)parentObj;
                allPmf = v93Obj.getConfigObject().getPmfObject().getPmfMap();

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
            Object parentObj = ADFUtils.getPageFlowScopeValue("parentObject");
            if (parentObj != null) {
                v93Obj = (V93kQuote)parentObj;
                allNodes = parseAllNodes(v93Obj);
            }
        }
        return allNodes;
    }

    public void fileUploadVCE(ValueChangeEvent vce) {
        System.out.println("Inside Value Listener");
        //Whenever a new file is uploaded , Set the page flow object to null and allNodes to null
        if (vce.getNewValue() != null) {
            ADFUtils.setSessionScopeValue("parentObject", null);
            allNodes = null;
            //Get File Object from VC Event
            UploadedFile fileVal = (UploadedFile)vce.getNewValue();
            //Upload File to path- Return actual server path
            InputStream inputStream = uploadFile(fileVal);
            parseXMLToPojo(inputStream);
            ResetUtils.reset(vce.getComponent());
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
            } catch (Exception ex) {
                // handle exception
                ex.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
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
            }
            if (v93Obj.getConfigObject().getModelbomObject().getSwlicensesObject() !=
                null) {
                swLicensesNodes =
                        v93Obj.getConfigObject().getModelbomObject().getSwlicensesObject().getSwlicenseItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getWtysupportObject() !=
                null) {
                wtySupportNodes =
                        v93Obj.getConfigObject().getModelbomObject().getWtysupportObject().getAllWtySupportItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getCaldiag() !=
                null) {
                calDiagNodes =
                        v93Obj.getConfigObject().getModelbomObject().getCaldiag().getCalDiagItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getRf() != null) {
                rfNodes =
                        v93Obj.getConfigObject().getModelbomObject().getRf().getAllRf();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDigitalObject() !=
                null) {
                digitalNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDigitalObject().getDigitalItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getMs() != null) {
                msNodes =
                        v93Obj.getConfigObject().getModelbomObject().getMs().getAllMs();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDpsObject() !=
                null) {
                dpsNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDpsObject().getDpsItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getDockingObject() !=
                null) {
                dockingNodes =
                        v93Obj.getConfigObject().getModelbomObject().getDockingObject().getAllDockingItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject() != null) {
                miscNodes =
                        v93Obj.getConfigObject().getModelbomObject().getMisc().getMiscItems();
            }
            //Inside ModelBom-->Infra
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getCoolingObject() !=
                null) {
                coolingNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getCoolingObject().getCoolingItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getDutifutilObject() !=
                null) {
                dufifUtilNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getDutifutilObject().getDutifUtilItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getManiObject() !=
                null) {
                maniNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getManiObject().getAllManiItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getTheadObject() !=
                null) {
                theadNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getTheadObject().getTheadItems();
            }
            if (v93Obj.getConfigObject().getModelbomObject().getInfraObject() !=
                null &&
                v93Obj.getConfigObject().getModelbomObject().getInfraObject().getWkstaObject() !=
                null) {
                wkstaNodes =
                        v93Obj.getConfigObject().getModelbomObject().getInfraObject().getWkstaObject().getWkstaItems();
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
        return nodeList;
    }

    public void parseXMLToPojo(InputStream inputStream) {
        V93kQuote parent = JaxbParser.jaxbXMLToObject(inputStream);
        String jsonStr = JSONUtils.convertObjToJson(parent);
        Object obj = null;
        //Reading JSOn from File to POJO
        ObjectMapper mapper = new ObjectMapper();
        try {
            String responseJson =
                (String)ConfiguratorUtils.callConfiguratorServlet(jsonStr);
            obj = mapper.readValue(responseJson, V93kQuote.class);
            ADFUtils.setSessionScopeValue("parentObject", obj);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
