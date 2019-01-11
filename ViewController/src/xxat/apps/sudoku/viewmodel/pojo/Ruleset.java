package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ruleset")
public class Ruleset {
    private String item;
    public Ruleset() {
        super();
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}
