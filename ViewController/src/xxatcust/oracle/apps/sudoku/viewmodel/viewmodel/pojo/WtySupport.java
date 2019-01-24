package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wty-support")
public class WtySupport {
    private String item;
    public WtySupport() {
        super();
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}
