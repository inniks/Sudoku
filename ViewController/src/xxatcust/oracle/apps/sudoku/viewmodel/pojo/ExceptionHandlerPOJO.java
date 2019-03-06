package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.TreeMap;

public class ExceptionHandlerPOJO  {
    private TreeMap<String,ArrayList<String>> errorMap ;
    private TreeMap<String,ArrayList<String>> errors ;
    private TreeMap<String, ArrayList<String>> notificationList;
    private TreeMap<String, ArrayList<String>> warningList;
    private ArrayList<String> debugMessages;
    private TreeMap<String, ArrayList<String>> debugMessageList;

    public ExceptionHandlerPOJO() {
        super();
    }

    public void setErrorMap(TreeMap<String, ArrayList<String>> errorMap) {
        this.errorMap = errorMap;
    }

    public TreeMap<String, ArrayList<String>> getErrorMap() {
        return errorMap;
    }

    public void setNotificationList(TreeMap<String, ArrayList<String>> notifications) {
        this.notificationList = notifications;
    }

    public TreeMap<String, ArrayList<String>> getNotificationList() {
        return notificationList;
    }

    public void setWarningList(TreeMap<String, ArrayList<String>> warnings) {
        this.warningList = warnings;
    }

    public TreeMap<String, ArrayList<String>> getWarningList() {
        return warningList;
    }

    public void setDebugMessageList(TreeMap<String, ArrayList<String>> debugMessages) {
        this.debugMessageList = debugMessages;
    }

    public TreeMap<String, ArrayList<String>> getDebugMessageList() {
        return debugMessageList;
    }

    public void setErrors(TreeMap<String, ArrayList<String>> errors) {
        this.errors = errors;
    }

    public TreeMap<String, ArrayList<String>> getErrors() {
        return errors;
    }

    public void setDebugMessages(ArrayList<String> debugMessages) {
        this.debugMessages = debugMessages;
    }

    public ArrayList<String> getDebugMessages() {
        return debugMessages;
    }
}
