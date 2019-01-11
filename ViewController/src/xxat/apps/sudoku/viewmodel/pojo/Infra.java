package xxat.apps.sudoku.viewmodel.pojo;

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

    public Thead getTheadObject() {
        return TheadObject;
    }

    public void setDutifutilObject(DutifUtil dutifutilObject) {
        this.dutifutilObject = dutifutilObject;
    }

    public DutifUtil getDutifutilObject() {
        return dutifutilObject;
    }

    public void setCoolingObject(Cooling CoolingObject) {
        this.CoolingObject = CoolingObject;
    }

    public Cooling getCoolingObject() {
        return CoolingObject;
    }

    public void setManiObject(Mani ManiObject) {
        this.ManiObject = ManiObject;
    }

    public Mani getManiObject() {
        return ManiObject;
    }

    public void setWkstaObject(Wksta WkstaObject) {
        this.WkstaObject = WkstaObject;
    }

    public Wksta getWkstaObject() {
        return WkstaObject;
    }
}
