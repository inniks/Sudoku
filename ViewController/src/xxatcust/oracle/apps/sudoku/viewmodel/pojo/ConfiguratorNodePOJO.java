package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.NONE)
public class ConfiguratorNodePOJO {
    @XmlValue
    String nodeName;
    String nodeDescription;
    String nodeValue;
    @XmlAttribute(name = "qty")
    String nodeQty;
    String unitPrice;
    String extendedPrice;
    String nodeCategory;
    String nodeColor;
    String printGroupLevel;
    String xmlTag;
    public ConfiguratorNodePOJO() {
        super();
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

    public void setNodeCategory(String nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public String getNodeCategory() {
        return nodeCategory;
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

    public void setXmlTag(String xmlTag) {
        this.xmlTag = xmlTag;
    }

    public String getXmlTag() {
        return xmlTag;
    }
}
