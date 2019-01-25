package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dps")
public class Dps {
    List < ConfiguratorNodePOJO > dpsItems = new ArrayList < ConfiguratorNodePOJO > ();
    public Dps() {
        super();
    }

    public void setDpsItems(List<ConfiguratorNodePOJO> dpsItems) {
        this.dpsItems = dpsItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getDpsItems() {
        return dpsItems;
    }
}
