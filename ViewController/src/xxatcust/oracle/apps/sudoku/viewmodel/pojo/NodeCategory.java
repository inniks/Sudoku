package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.List;

public class NodeCategory {
    private String category;
    private String nodeName ;
    List<NodeCategory> childNodes;


    public NodeCategory(String category,String nodeName) {
        this.nodeName = nodeName ;
        this.category = category;
        childNodes = new ArrayList<NodeCategory>();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setChildNodes(List<NodeCategory> childNodes) {
        this.childNodes = childNodes;
    }

    public List<NodeCategory> getChildNodes() {
        return childNodes;
    }
    // This methods directly add characters value in list
        public void addNodes(NodeCategory child) {
            childNodes.add(child) ;
        }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

}
