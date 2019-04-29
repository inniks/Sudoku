package xxatcust.oracle.apps.sudoku.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.DOMParser;
import xxatcust.oracle.apps.sudoku.util.StaxParser;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class LoadDynamicRegionBean {
    private String taskFlowId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/ConfiguratorFlow.xml#ConfiguratorFlow";
    private String quoteTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/QuotingFlow.xml#QuotingFlow";
    private String quoteTFUpdateId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/QuoteUpdateFlow.xml#QuoteUpdateFlow";
    private String viewReferenceTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/UploadXMLFlow.xml#UploadXMLFlow";
    private String targetRefTF =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/TargetConfigFlow.xml#TargetConfigFlow";
    private String currentTF = "configurator";
    private RichPopup expConfigPopup;
    private RichInputText fileNameBinding;
    private RichOutputFormatted exportException;

    public LoadDynamicRegionBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        if (this.getCurrentTF().equalsIgnoreCase("configurator")) {
            return TaskFlowId.parse(taskFlowId);
        } else if (this.getCurrentTF().equalsIgnoreCase("viewRef")) {
            return TaskFlowId.parse(viewReferenceTFId);
        } else if (this.getCurrentTF().equalsIgnoreCase("quoteUpdate")) {
            return TaskFlowId.parse(quoteTFUpdateId);
        } else if (this.getCurrentTF().equalsIgnoreCase("targetRef")) {
            return TaskFlowId.parse(targetRefTF);
        } else {
            return TaskFlowId.parse(quoteTFId);
        }

    }

    public void setTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String getTaskFlowId() {
        return taskFlowId;
    }

    public void setQuoteTFId(String quoteTFId) {
        this.quoteTFId = quoteTFId;
    }

    public String getQuoteTFId() {
        return quoteTFId;
    }

    public void setViewReferenceTFId(String viewReferenceTFId) {
        this.viewReferenceTFId = viewReferenceTFId;
    }

    public String getViewReferenceTFId() {
        return viewReferenceTFId;
    }

    public void setCurrentTF(String currentTF) {
        this.currentTF = currentTF;
    }

    public String getCurrentTF() {
        return currentTF;
    }

    public void viewReference(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String getNavString() {
        String importSource = null, quoteNumber = null, quoteNumFromSession =
            null;
        HashMap inputParamsMap =
            (HashMap)ADFUtils.getSessionScopeValue("inputParamsMap");
        if (inputParamsMap != null && !inputParamsMap.isEmpty()) {
            if (inputParamsMap.get("importSource") != null) {
                importSource = (String)inputParamsMap.get("importSource");
            }
            if (inputParamsMap.get("quoteNumber") != null) {
                quoteNumber = (String)inputParamsMap.get("quoteNumber");
            }
        }

        quoteNumFromSession =
                (String)ADFUtils.getSessionScopeValue("quoteNumber");
        if (importSource != null &&
            (importSource.equalsIgnoreCase("BUDGET_QUOTE") ||
             importSource.equalsIgnoreCase("FORMAL_QUOTE")|| importSource.equalsIgnoreCase("XML_FILE")) &&
            quoteNumFromSession != null)
            return "quoteUpdate";
        if (importSource == null || quoteNumFromSession == null)
            return "quote";

        if (importSource != null && importSource.equalsIgnoreCase("XML_FILE"))
            return "viewRef";
        else
            return "configurator";
    }

    public void cancelPopup(ActionEvent actionEvent) {
        expConfigPopup.cancel();
    }

    public void setQuoteTFUpdateId(String quoteTFUpdateId) {
        this.quoteTFUpdateId = quoteTFUpdateId;
    }

    public String getQuoteTFUpdateId() {
        return quoteTFUpdateId;
    }

    public void export(ActionEvent actionEvent) {
        // For now create the xml file and display in console
//        System.out.println("Export button pressed");
//        V93kQuote parentObj =
//            (V93kQuote)ADFUtils.getSessionScopeValue("parentObject");
//        if (parentObj == null) {
//            exportException.setValue("No configuration exists.");
//        } else {
//            File createdXml = DOMParser.XMLWriterDOM(parentObj);
//            ADFUtils.setPageFlowScopeValue("createdXML", createdXml);
//        }
    }

    public void setExpConfigPopup(RichPopup expConfigPopup) {
        this.expConfigPopup = expConfigPopup;
    }

    public RichPopup getExpConfigPopup() {
        return expConfigPopup;
    }

    public void setFileNameBinding(RichInputText fileNameBinding) {
        this.fileNameBinding = fileNameBinding;
    }

    public RichInputText getFileNameBinding() {
        return fileNameBinding;
    }

    public void setExportException(RichOutputFormatted exportException) {
        this.exportException = exportException;
    }

    public RichOutputFormatted getExportException() {
        return exportException;
    }

    public void fileDownloadListener(FacesContext facesContext,
                                     OutputStream outputStream) {
        System.out.println("Download Listener Fired");
        V93kQuote parentObj =
            (V93kQuote)ADFUtils.getSessionScopeValue("parentObject");
        File createdXml = null;
        if (parentObj == null) {
            exportException.setValue("No configuration exists.");
        } else {
            createdXml = DOMParser.XMLWriterDOM(parentObj);
            ADFUtils.setPageFlowScopeValue("createdXML", createdXml);
        }
        try {

            FileInputStream fis;
            byte[] b;
            fis = new FileInputStream(createdXml);

            int n;
            while ((n = fis.available()) > 0) {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
            outputStream.flush();
        } catch (FileNotFoundException fnfe) {
            // TODO: Add catch code
            ADFUtils.routeExceptions(fnfe);
            
        } catch (IOException ioe) {
            // TODO: Add catch code
           ADFUtils.routeExceptions(ioe);
        }
        finally{
            expConfigPopup.cancel();
        }
    }
}
