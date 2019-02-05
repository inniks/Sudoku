package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.HashMap;
import java.util.List;

public class ExceptionHandlerPOJO  {
    private HashMap<String,List<String>> errorMap ;
    public ExceptionHandlerPOJO() {
        super();
    }

    public void setErrorMap(HashMap<String, List<String>> errorMap) {
        this.errorMap = errorMap;
    }

    public HashMap<String, List<String>> getErrorMap() {
        return errorMap;
    }
}
