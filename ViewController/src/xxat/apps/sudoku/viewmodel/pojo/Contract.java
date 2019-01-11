package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contract")
public class Contract {
    private String conid;
    private String conpay;
    private String coninco;
    private String conwty;
    private String confixedpr;
    public Contract() {
        super();
    }

    public void setConid(String conid) {
        this.conid = conid;
    }

    public String getConid() {
        return conid;
    }

    public void setConpay(String conpay) {
        this.conpay = conpay;
    }

    public String getConpay() {
        return conpay;
    }

    public void setConinco(String coninco) {
        this.coninco = coninco;
    }

    public String getConinco() {
        return coninco;
    }

    public void setConwty(String conwty) {
        this.conwty = conwty;
    }

    public String getConwty() {
        return conwty;
    }

    public void setConfixedpr(String confixedpr) {
        this.confixedpr = confixedpr;
    }

    public String getConfixedpr() {
        return confixedpr;
    }
}
