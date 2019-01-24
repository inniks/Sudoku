package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

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
    public String getDealid() {
        return dealid;
    }

    public void setSalesch(String salesch) {
        this.salesch = salesch;
    }
    public String getSalesch() {
        return salesch;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setQuoteid(String quoteid) {
        this.quoteid = quoteid;
    }
    public String getQuoteid() {
        return quoteid;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
    }
    public String getSoNumber() {
        return soNumber;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }
    public String getSysid() {
        return sysid;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return currency;
    }

    public void setDdiscount(String ddiscount) {
        this.ddiscount = ddiscount;
    }
    public String getDdiscount() {
        return ddiscount;
    }

    public void setDincoterm(String dincoterm) {
        this.dincoterm = dincoterm;
    }
    public String getDincoterm() {
        return dincoterm;
    }

    public void setDpayterm(String dpayterm) {
        this.dpayterm = dpayterm;
    }
    public String getDpayterm() {
        return dpayterm;
    }

    public void setDwtyterm(String dwtyterm) {
        this.dwtyterm = dwtyterm;
    }
    public String getDwtyterm() {
        return dwtyterm;
    }

    public void setQdate(String qdate) {
        this.qdate = qdate;
    }
    public String getQdate() {
        return qdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
    public String getExpdate() {
        return expdate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }
    public String getOdate() {
        return odate;
    }

    public void setCrd(String crd) {
        this.crd = crd;
    }
    public String getCrd() {
        return crd;
    }
}
