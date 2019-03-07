package xxatcust.oracle.apps.sudoku.util;

import java.util.Comparator;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.NodeCategory;

public class NodeComparator implements Comparator<NodeCategory> {
    public NodeComparator() {
        super();
    }


    public int compare(NodeCategory o1, NodeCategory o2) {
        int flag = 0 ;
        if(o1.getPrintGroupLevel()!=null && o2.getPrintGroupLevel()!=null){
            flag = o1.getPrintGroupLevel().compareTo(o2.getPrintGroupLevel()) ;
        }
        return flag;
    }
}
