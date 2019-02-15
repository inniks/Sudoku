package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="digital")
public class Digital {
    List < ConfiguratorNodePOJO > digitalItems = new ArrayList < ConfiguratorNodePOJO > ();
    public Digital() {
        super();
    }


    public void setDigitalItems(List<ConfiguratorNodePOJO> digitalItems) {
        this.digitalItems = digitalItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getDigitalItems() {
        return digitalItems;
    }
}
