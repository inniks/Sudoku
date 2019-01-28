package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mani")
public class Mani {
    private List<ConfiguratorNodePOJO> allManiItems;
    public Mani() {
        super();
    }

    public void setAllManiItems(List<ConfiguratorNodePOJO> allManiItems) {
        this.allManiItems = allManiItems;
    }

    public List<ConfiguratorNodePOJO> getAllManiItems() {
        return allManiItems;
    }
}
