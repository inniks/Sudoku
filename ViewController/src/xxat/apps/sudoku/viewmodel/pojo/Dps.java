package xxat.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dps")
public class Dps {
    ArrayList < Object > item = new ArrayList < Object > ();
    public Dps() {
        super();
    }

    public void setItem(ArrayList<Object> item) {
        this.item = item;
    }

    public ArrayList<Object> getItem() {
        return item;
    }
}
