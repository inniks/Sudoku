package xxat.apps.sudoku.bean;

public class SudokuTemplateBean {
    public SudokuTemplateBean() {
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
