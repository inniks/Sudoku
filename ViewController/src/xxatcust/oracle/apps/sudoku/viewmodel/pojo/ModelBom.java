package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "modelbom")
public class ModelBom {
    private String cid;
    private String pricelist;
    private XClass ClassObject;
    private Ruleset RulesetObject;
    private Infra InfraObject;
    private Digital DigitalObject;
    private Dps DpsObject;
    private Ms ms;
    private Rf rf;
    private SwLicenses swlicensesObject;
    private Docking DockingObject;
    private Misc misc;
    private CalDiag caldiag;
    private WtySupport wtysupportObject;
    private InfraUpgrade infraUpgradeObject;
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

    public void setMs(Ms ms) {
        this.ms = ms;
    }
    @XmlElement(name="ms")
    public Ms getMs() {
        return ms;
    }

    public void setRf(Rf rf) {
        this.rf = rf;
    }

    @XmlElement(name = "rf")
    public Rf getRf() {
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

    public void setMisc(Misc misc) {
        this.misc = misc;
    }

    @XmlElement(name = "misc")
    public Misc getMisc() {
        return misc;
    }

    public void setCaldiag(CalDiag caldiag) {
        this.caldiag = caldiag;
    }

    @XmlElement(name = "cal-diag")
    public CalDiag getCaldiag() {
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

    public void setInfraUpgradeObject(InfraUpgrade infraUpgradeObject) {
        this.infraUpgradeObject = infraUpgradeObject;
    }

    @XmlElement(name = "infra-upgrade")
    public InfraUpgrade getInfraUpgradeObject() {
        return infraUpgradeObject;
    }
}
