package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

public class CalDiag {
    List<ConfiguratorNodePOJO> calDiagItems ;
    public CalDiag() {
        super();
    }

    public void setCalDiagItems(List<ConfiguratorNodePOJO> calDiagItems) {
        this.calDiagItems = calDiagItems;
    }

    public List<ConfiguratorNodePOJO> getCalDiagItems() {
        return calDiagItems;
    }
}

