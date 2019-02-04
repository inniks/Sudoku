package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ms")
public class Ms {
    List<ConfiguratorNodePOJO> allMs ;
    public Ms() {
        super();
    }


    public void setAllMs(List<ConfiguratorNodePOJO> allMs) {
        this.allMs = allMs;
    }
    @XmlElement(name = "item")
    public List<ConfiguratorNodePOJO> getAllMs() {
        return allMs;
    }
}
