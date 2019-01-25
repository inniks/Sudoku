package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cooling")
public class Cooling {
    List < ConfiguratorNodePOJO > coolingItems = new ArrayList < ConfiguratorNodePOJO > ();
    public Cooling() {
        super();
    }


    public void setCoolingItems(List<ConfiguratorNodePOJO> coolingItems) {
        this.coolingItems = coolingItems;
    }
    @XmlElement(name = "item")
    public List<ConfiguratorNodePOJO> getCoolingItems() {
        return coolingItems;
    }
}
