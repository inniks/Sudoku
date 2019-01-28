package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rf")
public class Rf {
    List<ConfiguratorNodePOJO> allRf ;
    public Rf() {
        super();
    }

    public void setAllRf(List<ConfiguratorNodePOJO> allRf) {
        this.allRf = allRf;
    }

    public List<ConfiguratorNodePOJO> getAllRf() {
        return allRf;
    }
}
