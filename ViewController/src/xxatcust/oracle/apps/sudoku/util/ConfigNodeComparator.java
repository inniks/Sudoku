package xxatcust.oracle.apps.sudoku.util;

import java.util.Comparator;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.ConfiguratorNodePOJO;

public class ConfigNodeComparator implements Comparator<ConfiguratorNodePOJO> {
    public ConfigNodeComparator() {
        super();
    }

   
    public int compare(ConfiguratorNodePOJO o1, ConfiguratorNodePOJO o2) {
        int flag = 0 ;
        if(o1.getPrintGroupLevel()!=null && o2.getPrintGroupLevel()!=null){
            flag = o1.getPrintGroupLevel().compareTo(o2.getPrintGroupLevel());
        }
        return flag;
    }
}
