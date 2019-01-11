package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="deal")
public class Deal {
    private String dealid;
     private String salesch;
     private String status;
     private String quoteid;
     private String soNumber;
     private String sysid;
     private String currency;
     private String ddiscount;
     private String dincoterm;
     private String dpayterm;
     private String dwtyterm;
     private String qdate;
     private String expdate;
     private String odate;
     private String crd;
    public Deal() {
        super();
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }
@XmlElement(name = "dealid")
    public String getDealid() {
        return dealid;
    }

    public void setSalesch(String salesch) {
        this.salesch = salesch;
    }
    @XmlElement
    public String getSalesch() {
        return salesch;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @XmlElement
    public String getStatus() {
        return status;
    }

    public void setQuoteid(String quoteid) {
        this.quoteid = quoteid;
    }
    @XmlElement
    public String getQuoteid() {
        return quoteid;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
    }
    @XmlElement
    public String getSoNumber() {
        return soNumber;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }
    @XmlElement
    public String getSysid() {
        return sysid;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    @XmlElement
    public String getCurrency() {
        return currency;
    }

    public void setDdiscount(String ddiscount) {
        this.ddiscount = ddiscount;
    }
    @XmlElement
    public String getDdiscount() {
        return ddiscount;
    }

    public void setDincoterm(String dincoterm) {
        this.dincoterm = dincoterm;
    }
    @XmlElement
    public String getDincoterm() {
        return dincoterm;
    }

    public void setDpayterm(String dpayterm) {
        this.dpayterm = dpayterm;
    }
    @XmlElement
    public String getDpayterm() {
        return dpayterm;
    }

    public void setDwtyterm(String dwtyterm) {
        this.dwtyterm = dwtyterm;
    }
    @XmlElement
    public String getDwtyterm() {
        return dwtyterm;
    }

    public void setQdate(String qdate) {
        this.qdate = qdate;
    }
    @XmlElement
    public String getQdate() {
        return qdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
    @XmlElement
    public String getExpdate() {
        return expdate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }
    @XmlElement
    public String getOdate() {
        return odate;
    }

    public void setCrd(String crd) {
        this.crd = crd;
    }
    @XmlElement
    public String getCrd() {
        return crd;
    }
}
