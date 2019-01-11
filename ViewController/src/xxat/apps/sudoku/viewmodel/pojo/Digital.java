package xxat.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="digital")
public class Digital {
    ArrayList < Object > item = new ArrayList < Object > ();
    public Digital() {
        super();
    }

    public void setItem(ArrayList<Object> item) {
        this.item = item;
    }

    public ArrayList<Object> getItem() {
        return item;
    }
}
