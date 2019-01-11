package xxat.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="customer")
public class Customer {
    private String globalcmpy;
     private String cname;
     private String cnumber;
     private String ccontact;
     private String cemail;
     private String cphone;
     private String caddr1;
     private String caddr2;
    public Customer() {
        super();
    }

    public void setGlobalcmpy(String globalcmpy) {
        this.globalcmpy = globalcmpy;
    }

    public String getGlobalcmpy() {
        return globalcmpy;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public String getCnumber() {
        return cnumber;
    }

    public void setCcontact(String ccontact) {
        this.ccontact = ccontact;
    }

    public String getCcontact() {
        return ccontact;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCaddr1(String caddr1) {
        this.caddr1 = caddr1;
    }

    public String getCaddr1() {
        return caddr1;
    }

    public void setCaddr2(String caddr2) {
        this.caddr2 = caddr2;
    }

    public String getCaddr2() {
        return caddr2;
    }
}
