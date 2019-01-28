package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "docking")
public class Docking {
    private List<ConfiguratorNodePOJO> allDockingItems;
    public Docking() {
        super();
    }

    public void setAllDockingItems(List<ConfiguratorNodePOJO> allDockingItems) {
        this.allDockingItems = allDockingItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getAllDockingItems() {
        return allDockingItems;
    }
}
