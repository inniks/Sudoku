package xxat.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pmf")
public class Pmf {
    String refId = null; //it is ref-id
    String productName = null; //it is card
    String pogoName = null; //it is pogo
    String pogoMapping = null;
    //ArrayList < Object > map = new ArrayList < Object > ();
    public Pmf() {
        super();
    }

//    public void setMap(ArrayList<Object> map) {
//        this.map = map;
//    }
//    public ArrayList<Object> getMap() {
//        return map;
//    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRefId() {
        return refId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setPogoName(String pogoName) {
        this.pogoName = pogoName;
    }

    public String getPogoName() {
        return pogoName;
    }

    public void setPogoMapping(String pogoMapping) {
        this.pogoMapping = pogoMapping;
    }

    public String getPogoMapping() {
        return pogoMapping;
    }
}
