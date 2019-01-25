package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class Config {
    ModelBom ModelbomObject;
    Pmf PmfObject;
    private String _version;

    public Config() {
        super();
    }

    public void setModelbomObject(ModelBom ModelbomObject) {
        this.ModelbomObject = ModelbomObject;
    }

    @XmlElement(name = "modelbom")
    public ModelBom getModelbomObject() {
        return ModelbomObject;
    }

    public void setPmfObject(Pmf PmfObject) {
        this.PmfObject = PmfObject;
    }

    @XmlElement(name = "pmf")
    public Pmf getPmfObject() {
        return PmfObject;
    }

    public void setVersion(String _version) {
        this._version = _version;
    }

    @XmlAttribute(name = "version")
    public String getVersion() {
        return _version;
    }
}
