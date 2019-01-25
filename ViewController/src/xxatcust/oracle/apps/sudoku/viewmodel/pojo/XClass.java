package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class XClass {
    private String _id;
     private String _compatibility;
    public XClass() {
        super();
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getId() {
        return _id;
    }

    public void setCompatibility(String _compatibility) {
        this._compatibility = _compatibility;
    }

    public String getCompatibility() {
        return _compatibility;
    }
}
