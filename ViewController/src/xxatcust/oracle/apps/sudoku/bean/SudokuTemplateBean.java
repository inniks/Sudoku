package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;

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
    public void controllerExceptionHandler() {
        System.out.println("Inside Exception Handler");
        ControllerContext context = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = context.getCurrentRootViewPort();
        Exception exceptionData = currentRootViewPort.getExceptionData();
        
        if (currentRootViewPort.isExceptionPresent()) {
            exceptionData.printStackTrace();
            currentRootViewPort.clearException();
          
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, exceptionData.getMessage(), null));
        }
    }
}
