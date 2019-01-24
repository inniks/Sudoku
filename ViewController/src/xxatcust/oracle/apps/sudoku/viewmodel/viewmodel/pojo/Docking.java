package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "docking")
public class Docking {
    private String item;
    public Docking() {
        super();
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}
