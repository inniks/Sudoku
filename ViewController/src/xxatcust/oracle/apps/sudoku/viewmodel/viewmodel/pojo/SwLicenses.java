package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import java.util.ArrayList;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sw-licenses")
public class SwLicenses {
    List < ConfiguratorNodePOJO > swlicenseItems = new ArrayList < ConfiguratorNodePOJO > ();
    public SwLicenses() {
        super();
    }


    public void setSwlicenseItems(List<ConfiguratorNodePOJO> swlicenseItems) {
        this.swlicenseItems = swlicenseItems;
    }
    @XmlElement(name="item")
    public List<ConfiguratorNodePOJO> getSwlicenseItems() {
        return swlicenseItems;
    }
}
