package xxat.apps.sudoku.bean;

public class TemplateBean {
    public TemplateBean() {
        super();
    }

    private long taskFlowParam = 0;
    private String InitSesnScope = "";

    public long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public void setTaskFlowParam(long taskFlowParam) {
        this.taskFlowParam = taskFlowParam;
    }

    public long getTaskFlowParam() {
        return taskFlowParam;
    }
}
