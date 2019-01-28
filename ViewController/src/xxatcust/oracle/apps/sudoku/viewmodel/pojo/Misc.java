package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="misc")
public class Misc {
    List<ConfiguratorNodePOJO> miscItems ;
    public Misc() {
        super();
    }

    public void setMiscItems(List<ConfiguratorNodePOJO> miscItems) {
        this.miscItems = miscItems;
    }

    public List<ConfiguratorNodePOJO> getMiscItems() {
        return miscItems;
    }
}
