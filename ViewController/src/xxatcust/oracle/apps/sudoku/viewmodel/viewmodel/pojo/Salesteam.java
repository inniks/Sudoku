package xxatcust.oracle.apps.sudoku.viewmodel.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="salesteam")
public class Salesteam {
    private String ou;
    private String srespo;
    private String sphone;
    private String semail;
    private String csrrespo;
    private String csrphone;
    private String csremail;
    public Salesteam() {
        super();
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getOu() {
        return ou;
    }

    public void setSrespo(String srespo) {
        this.srespo = srespo;
    }

    public String getSrespo() {
        return srespo;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSemail() {
        return semail;
    }

    public void setCsrrespo(String csrrespo) {
        this.csrrespo = csrrespo;
    }

    public String getCsrrespo() {
        return csrrespo;
    }

    public void setCsrphone(String csrphone) {
        this.csrphone = csrphone;
    }

    public String getCsrphone() {
        return csrphone;
    }

    public void setCsremail(String csremail) {
        this.csremail = csremail;
    }

    public String getCsremail() {
        return csremail;
    }
}
