package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.List;

public class NodeCategory {
    private String category;
    private String nodeName ;
    String nodeDescription;
    String nodeValue;
    String nodeQty;
    String unitPrice;
    String extendedPrice;
    String uom ;
    String nodeColor;
    List<NodeCategory> childNodes;
    String printGroupLevel ;

    public NodeCategory(String category,String nodeName,String nodeDescription,String nodeQty,String nodeValue,String unitPrice,String extendedPrice,String nodeColor,String printGrpLevel) {
        this.nodeName = nodeName ;
        this.category = category;
        this.nodeDescription = nodeDescription ;
        this.nodeQty = nodeQty ;
        this.nodeValue = nodeValue ;
        this.unitPrice = unitPrice ;
        this.extendedPrice = extendedPrice ;
        this.uom = "EA" ;
        this.nodeColor = nodeColor ;
        this.printGroupLevel = printGrpLevel ;
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

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeQty(String nodeQty) {
        this.nodeQty = nodeQty;
    }

    public String getNodeQty() {
        return nodeQty;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setExtendedPrice(String extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public String getExtendedPrice() {
        return extendedPrice;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUom() {
        return uom;
    }

    public void setNodeColor(String nodeColor) {
        this.nodeColor = nodeColor;
    }

    public String getNodeColor() {
        return nodeColor;
    }

    public void setPrintGroupLevel(String printGroupLevel) {
        this.printGroupLevel = printGroupLevel;
    }

    public String getPrintGroupLevel() {
        return printGroupLevel;
    }
}
