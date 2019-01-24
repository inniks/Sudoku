package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="thead")
public class Thead {

    List < ConfiguratorNodePOJO > theadItems = new ArrayList < ConfiguratorNodePOJO > ();
    public Thead() {
        super();
    }

    public void setTheadItems(List<ConfiguratorNodePOJO> theadItems) {
        this.theadItems = theadItems;
    }
    @XmlElement(name = "item")
    public List<ConfiguratorNodePOJO> getTheadItems() {
        return theadItems;
    }
}
