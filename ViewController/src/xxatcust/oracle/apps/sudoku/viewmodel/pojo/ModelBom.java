package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "modelbom")
public class ModelBom {
    private String cid;
    private String pricelist;
    XClass ClassObject;
    Ruleset RulesetObject;
    Infra InfraObject;
    Digital DigitalObject;
    Dps DpsObject;
    private String ms;
    private String rf;
    SwLicenses swlicensesObject;
    Docking DockingObject;
    private String misc;
    private String caldiag;
    WtySupport wtysupportObject;
    private String _id;

    public ModelBom() {
        super();
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setPricelist(String pricelist) {
        this.pricelist = pricelist;
    }

    public String getPricelist() {
        return pricelist;
    }

    public void setClassObject(XClass ClassObject) {
        this.ClassObject = ClassObject;
    }

    @XmlElement(name = "class")
    public XClass getClassObject() {
        return ClassObject;
    }

    public void setRulesetObject(Ruleset RulesetObject) {
        this.RulesetObject = RulesetObject;
    }

    @XmlElement(name = "ruleset")
    public Ruleset getRulesetObject() {
        return RulesetObject;
    }

    public void setInfraObject(Infra InfraObject) {
        this.InfraObject = InfraObject;
    }

    @XmlElement(name = "infra")
    public Infra getInfraObject() {
        return InfraObject;
    }

    public void setDigitalObject(Digital DigitalObject) {
        this.DigitalObject = DigitalObject;
    }

    @XmlElement(name = "digital")
    public Digital getDigitalObject() {
        return DigitalObject;
    }

    public void setDpsObject(Dps DpsObject) {
        this.DpsObject = DpsObject;
    }

    @XmlElement(name = "dps")
    public Dps getDpsObject() {
        return DpsObject;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getMs() {
        return ms;
    }

    public void setRf(String rf) {
        this.rf = rf;
    }

    public String getRf() {
        return rf;
    }

    public void setSwlicensesObject(SwLicenses swlicensesObject) {
        this.swlicensesObject = swlicensesObject;
    }

    @XmlElement(name = "sw-licenses")
    public SwLicenses getSwlicensesObject() {
        return swlicensesObject;
    }

    public void setDockingObject(Docking DockingObject) {
        this.DockingObject = DockingObject;
    }

    @XmlElement(name = "docking")
    public Docking getDockingObject() {
        return DockingObject;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    public String getMisc() {
        return misc;
    }

    public void setCaldiag(String caldiag) {
        this.caldiag = caldiag;
    }

    @XmlAttribute(name = "cal-diag")
    public String getCaldiag() {
        return caldiag;
    }

    public void setWtysupportObject(WtySupport wtysupportObject) {
        this.wtysupportObject = wtysupportObject;
    }

    @XmlElement(name = "wty-support")
    public WtySupport getWtysupportObject() {
        return wtysupportObject;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @XmlAttribute(name = "id")
    public String getId() {
        return _id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
