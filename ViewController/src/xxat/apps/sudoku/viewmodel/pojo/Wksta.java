package xxat.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wksta")
public class Wksta {
    List < ConfiguratorNodePOJO > wkstaItems = new ArrayList < ConfiguratorNodePOJO > ();
    public Wksta() {
        super();
    }


    public void setWkstaItems(List<ConfiguratorNodePOJO> wkstaItems) {
        this.wkstaItems = wkstaItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getWkstaItems() {
        return wkstaItems;
    }
}
