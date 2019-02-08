package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExceptionHandlerPOJO  {
    private HashMap<String,ArrayList<String>> errorMap ;
    public ExceptionHandlerPOJO() {
        super();
    }

    public void setErrorMap(HashMap<String, ArrayList<String>> errorMap) {
        this.errorMap = errorMap;
    }

    public HashMap<String, ArrayList<String>> getErrorMap() {
        return errorMap;
    }
}
