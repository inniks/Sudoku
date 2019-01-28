package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wty-support")
public class WtySupport {
    private List<ConfiguratorNodePOJO> allWtySupportItems;
    public WtySupport() {
        super();
    }


    public void setAllWtySupportItems(List<ConfiguratorNodePOJO> allWtySupportItems) {
        this.allWtySupportItems = allWtySupportItems;
    }

    public List<ConfiguratorNodePOJO> getAllWtySupportItems() {
        return allWtySupportItems;
    }
}
