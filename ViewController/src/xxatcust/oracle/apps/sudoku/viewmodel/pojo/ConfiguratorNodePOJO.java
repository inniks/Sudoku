package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import xxatcust.oracle.apps.sudoku.util.StringTrimAdapter;

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

    public ConfiguratorNodePOJO() {
        super();
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public String getNodeName() {
        
        if(nodeName!=null){
            //nodeName = removeLeadingSpaces(nodeName) ;
            //System.out.println(nodeName);
            nodeName = StringTrimAdapter.removeLeadingSpaces(nodeName) ;
            nodeName = StringTrimAdapter.stripSpaces(nodeName) ;
        }
       
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
   
}
