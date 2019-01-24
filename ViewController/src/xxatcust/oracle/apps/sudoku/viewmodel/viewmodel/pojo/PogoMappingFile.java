package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map")
public class PogoMappingFile {
    String refId = null; //it is ref-id
    String productName = null; //it is card
    String pogoName = null; //it is pogo
    String pogoMapping = null;

    public PogoMappingFile() {
        super();
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
    @XmlAttribute(name = "ref-id")
    public String getRefId() {
        return refId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @XmlAttribute(name = "card")
    public String getProductName() {
        return productName;
    }

    public void setPogoName(String pogoName) {
        this.pogoName = pogoName;
    }
    @XmlAttribute(name = "cable")
    public String getPogoName() {
        return pogoName;
    }

    public void setPogoMapping(String pogoMapping) {
        this.pogoMapping = pogoMapping;
    }
    @XmlAttribute(name = "")
    public String getPogoMapping() {
        return pogoMapping;
    }
}
