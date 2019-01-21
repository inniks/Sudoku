package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "infra")
public class Infra {
    Thead TheadObject;
     DutifUtil dutifutilObject;
     Cooling CoolingObject;
     Mani ManiObject;
     Wksta WkstaObject;
    public Infra() {
        super();
    }

    public void setTheadObject(Thead TheadObject) {
        this.TheadObject = TheadObject;
    }
    @XmlElement(name = "thead")
    public Thead getTheadObject() {
        return TheadObject;
    }

    public void setDutifutilObject(DutifUtil dutifutilObject) {
        this.dutifutilObject = dutifutilObject;
    }
    @XmlElement(name = "dutif-util")
    public DutifUtil getDutifutilObject() {
        return dutifutilObject;
    }

    public void setCoolingObject(Cooling CoolingObject) {
        this.CoolingObject = CoolingObject;
    }
    @XmlElement(name = "cooling")
    public Cooling getCoolingObject() {
        return CoolingObject;
    }

    public void setManiObject(Mani ManiObject) {
        this.ManiObject = ManiObject;
    }
    @XmlElement(name = "mani")
    public Mani getManiObject() {
        return ManiObject;
    }

    public void setWkstaObject(Wksta WkstaObject) {
        this.WkstaObject = WkstaObject;
    }
    @XmlElement(name = "wksta")
    public Wksta getWkstaObject() {
        return WkstaObject;
    }
}
