package xxatcust.oracle.apps.sudoku.bean.bean;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;

import xxatcust.oracle.apps.sudoku.util.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.PogoMappingFile;
import xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo.V93kQuote;

public class XMLImportPageBean {
    List<PogoMappingFile> allPmf;
    private RichTable pmfTable;
    V93kQuote v93Obj ;
    public XMLImportPageBean() {
        super();
    }

    public void setAllPmf(List<PogoMappingFile> allPmf) {
       
        this.allPmf = allPmf;
    }

    public List<PogoMappingFile> getAllPmf() {
        if(allPmf==null){
            Object parentObj = ADFUtils.getPageFlowScopeValue("parentObject");
            if(parentObj!=null){
               v93Obj = (V93kQuote)parentObj; 
               allPmf = v93Obj.getConfigObject().getPmfObject().getPmfMap() ;
                
            }
        }
        return allPmf;
        //System.out.println(allPmf);
        
    }

    public void setPmfTable(RichTable pmfTable) {
        this.pmfTable = pmfTable;
    }

    public RichTable getPmfTable() {
        return pmfTable;
    }
}
