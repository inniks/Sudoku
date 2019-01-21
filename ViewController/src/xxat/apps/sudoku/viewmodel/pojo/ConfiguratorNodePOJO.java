package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class ConfiguratorNodePOJO {
    String nodeName;
    String nodeDescription;
    String nodeValue;
    String nodeQty;
    String unitPrice;
    String extendedPrice;
    String nodeCategory;

    public ConfiguratorNodePOJO() {
        super();
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    @XmlAttribute(name = "")
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
    @XmlAttribute(name="qty")
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

    public void setNodeCategory(String nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public String getNodeCategory() {
        return nodeCategory;
    }
}
