package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "qheader")
public class QHeader {
    private String qtitle;
    Customer CustomerObject;
    Contract ContractObject;
    Deal DealObject;
    Salesteam SalesteamObject;

    public QHeader() {
        super();
    }

    public void setQtitle(String qtitle) {
        this.qtitle = qtitle;
    }

    public String getQtitle() {
        return qtitle;
    }

    public void setCustomerObject(Customer CustomerObject) {
        this.CustomerObject = CustomerObject;
    }

    @XmlElement(name = "customer")
    public Customer getCustomerObject() {
        return CustomerObject;
    }

    public void setContractObject(Contract ContractObject) {
        this.ContractObject = ContractObject;
    }

    @XmlElement(name = "contract")
    public Contract getContractObject() {
        return ContractObject;
    }

    public void setDealObject(Deal DealObject) {
        this.DealObject = DealObject;
    }
@XmlElement(name = "deal")
    public Deal getDealObject() {
        return DealObject;
    }

    public void setSalesteamObject(Salesteam SalesteamObject) {
        this.SalesteamObject = SalesteamObject;
    }
@XmlElement(name = "salesteam")
    public Salesteam getSalesteamObject() {
        return SalesteamObject;
    }
}
