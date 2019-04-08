package xxatcust.oracle.apps.sudoku.bean;

import oracle.adf.controller.TaskFlowId;

public class DynamicBinding {
    private String configuratorTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/ConfiguratorFlow.xml#ConfiguratorFlow";
    private String quoteTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/QuotingFlow.xml#QuotingFlow";
    private String viewReferenceTFId =
        "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/UploadXMLFlow.xml#UploadXMLFlow";
    String currentTF = "configurator";

    public DynamicBinding() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        System.out.println("enter to TaskFLow getDynamicTaskFlowId " +
                           this.getCurrentTF());
        if (this.getCurrentTF().equalsIgnoreCase("configurator")) {
            System.out.println("Return config");
            return TaskFlowId.parse(configuratorTFId);
        } else if (this.getCurrentTF().equalsIgnoreCase("viewRef")) {
            System.out.println("Return View Ref");
            return TaskFlowId.parse(viewReferenceTFId);
        } else

        {
            System.out.println("Return Quote");
            return TaskFlowId.parse(quoteTFId);
        }
    }

    public String getConfiguratorTFId() {
        return configuratorTFId;
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

    public String getCurrentTF() {
        return currentTF;
    }

    public void setCurrentTF(String currentTF) {
        this.currentTF = currentTF;
    }
}
