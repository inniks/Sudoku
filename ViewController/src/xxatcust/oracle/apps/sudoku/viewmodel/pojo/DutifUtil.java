package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dutif-util")
public class DutifUtil {
    List < ConfiguratorNodePOJO > dutifUtilItems = new ArrayList < ConfiguratorNodePOJO > ();
    public DutifUtil() {
        super();
    }

    public void setDutifUtilItems(List<ConfiguratorNodePOJO> dutifUtilItems) {
        this.dutifUtilItems = dutifUtilItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getDutifUtilItems() {
        return dutifUtilItems;
    }
}
