package xxatcust.oracle.apps.sudoku.bean;

import oracle.adf.controller.TaskFlowId;

public class DynamicRegionBinding {
    private String taskFlowId = "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/UploadXMLFlow.xml#UploadXMLFlow";

    public DynamicRegionBinding() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String quotingFlow() {
        taskFlowId = "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/QuotingFlow.xml#QuotingFlow";
        return null;
    }

    public String importSrcFlow() {
        taskFlowId = "/WEB-INF/xxatcust/oracle/apps/sudoku/pageFlows/ImportSrcFlow.xml#ImportSrcFlow";
        return null;
    }
}
