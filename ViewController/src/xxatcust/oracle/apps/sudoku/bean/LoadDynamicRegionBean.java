package xxatcust.oracle.apps.sudoku.bean;

import java.util.HashMap;

import javax.faces.event.ActionEvent;

import oracle.adf.controller.TaskFlowId;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;

public class LoadDynamicRegionBean {
    private String taskFlowId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/ConfiguratorFlow.xml#ConfiguratorFlow";
    private String quoteTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/QuotingFlow.xml#QuotingFlow";
    private String viewReferenceTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/UploadXMLFlow.xml#UploadXMLFlow";
    private String currentTF = "configurator";

    public LoadDynamicRegionBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        System.out.println("enter to TaskFLow getDynamicTaskFlowId " +
                           this.getCurrentTF());
        if (this.getCurrentTF().equalsIgnoreCase("configurator")) {
            System.out.println("Return config");
            return TaskFlowId.parse(taskFlowId);
        } else if (this.getCurrentTF().equalsIgnoreCase("viewRef")) {
            System.out.println("Return View Ref");
            return TaskFlowId.parse(viewReferenceTFId);
        } else

        {
            System.out.println("Return Quote");
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
        String importSource = null;
        HashMap inputParamsMap =
            (HashMap)ADFUtils.getSessionScopeValue("inputParamsMap");
        if (inputParamsMap != null && !inputParamsMap.isEmpty()) {
            if (inputParamsMap.get("importSource") != null) {
                importSource = (String)inputParamsMap.get("importSource");
            }
//            if (inputParamsMap.get("quoteNumber") != null) {
//                quoteNumber = (String)inputParamsMap.get("quoteNumber");
//            }
//            if (inputParamsMap.get("reuseQuote") != null) {
//                reuseQuote = (Boolean)inputParamsMap.get("reuseQuote");
//            }
//            if (inputParamsMap.get("copyRefConf") != null) {
//                copyRefConf = (Boolean)inputParamsMap.get("copyRefConf");
//            }
        }
        if (importSource != null &&
            (importSource.equalsIgnoreCase("BUDGET_QUOTE") ||
             importSource.equalsIgnoreCase("FORMAL_QUOTE")))
            return "quote";
        if (importSource != null && importSource.equalsIgnoreCase("XML_FILE"))
            return "viewRef";
        else
            return "configurator";
    }
}
