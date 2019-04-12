package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


public class QuotingBean {
    private RichInputListOfValues bindOrderType;
    private RichInputListOfValues bindCustomerName;
    private RichInputListOfValues bindCustNumber;
    private boolean isCustEnable = false;
    private boolean businessAgrement = false;

    public QuotingBean() {
        super();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void saveQuoteDetails(ActionEvent actionEvent) {
        String msg = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        OperationBinding operationBinding =
            getBindings().getOperationBinding("callQuoteAPI");
        if (operationBinding.getErrors().size() == 0)
            msg = (String)operationBinding.execute();
        FacesMessage message = new FacesMessage(msg);
        if (msg.contains("<html><body>")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
        } else if (msg.contains("A new quote has been created")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            fc.addMessage(null, message);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
        }
    }

    public String clearFields() {
        isCustEnable = false;
        businessAgrement = false;
        //        OperationBinding ob = getBindings().getOperationBinding("clearQuoteFields");
        //        isCustEnable = false;
        //        businessAgrement= false;
        //        if(ob.getErrors().size()==0){
        //            ob.execute();
        //            }

        return "clear";
    }

    public void searchQuote(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("searchQuote");
        //        if(ob.getErrors().size()==0){
        ob.execute();
    }

    public void custmoerSupportVCE(ValueChangeEvent valueChangeEvent) {
        System.out.println(valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            valueChangeEvent.getNewValue() != null) {
            OperationBinding ob =
                getBindings().getOperationBinding("getFaxNum");
            //        if(ob.getErrors().size()==0){
            ob.execute();
        }
    }

    public void custNameVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() &&
            valueChangeEvent.getNewValue() != null) {
            businessAgrement = true;
            //        OperationBinding ob = getBindings().getOperationBinding("getQuoteCustmerAddress");
            ////                if(ob.getErrors().size()==0){
            //            ob.execute();
        } else {
            businessAgrement = false;
        }
    }

    public void custNameRPL(ReturnPopupEvent returnPopupEvent) {
        if (returnPopupEvent != null) {
            System.out.println("rop:" + returnPopupEvent);
            OperationBinding ob =
                getBindings().getOperationBinding("getQuoteCustmerAddress");
            //                if(ob.getErrors().size()==0){
            ob.execute();
        }
    }

    public void ouVCE(ValueChangeEvent vce) {
        if (vce.getOldValue() != vce.getNewValue() &&
            vce.getNewValue() != null) {
            isCustEnable = true;
            getBindOrderType().setValue(null);
            getBindCustomerName().setValue(null);
            getBindCustNumber().setValue(null);

        } else {
            isCustEnable = false;
        }
    }

    public String updateQuote() {
        //getUpdateQuote
        OperationBinding ob =
            getBindings().getOperationBinding("getUpdateQuote");
        if (ob.getErrors().size() == 0) {
            ob.execute();
        }
        return "update";
    }


    public void setBindOrderType(RichInputListOfValues bindOrderType) {
        this.bindOrderType = bindOrderType;
    }

    public RichInputListOfValues getBindOrderType() {
        return bindOrderType;
    }

    public void setBindCustomerName(RichInputListOfValues bindCustomerName) {
        this.bindCustomerName = bindCustomerName;
    }

    public RichInputListOfValues getBindCustomerName() {
        return bindCustomerName;
    }

    public void setBindCustNumber(RichInputListOfValues bindCustNumber) {
        this.bindCustNumber = bindCustNumber;
    }

    public RichInputListOfValues getBindCustNumber() {
        return bindCustNumber;
    }

    public void setIsCustEnable(boolean isCustEnable) {
        this.isCustEnable = isCustEnable;
    }

    public boolean isIsCustEnable() {
        return isCustEnable;
    }

    public void setBusinessAgrement(boolean businessAgrement) {
        this.businessAgrement = businessAgrement;
    }

    public boolean isBusinessAgrement() {
        return businessAgrement;
    }


    public void updateQuote(ActionEvent actionEvent) {
        String msg = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        OperationBinding operationBinding =
            getBindings().getOperationBinding("callUpdateQuoteAPI");
        if (operationBinding.getErrors().size() == 0)
            msg = (String)operationBinding.execute();
        FacesMessage message = new FacesMessage(msg);
        if (msg.contains("<html><body>")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
        }
                else if(msg.contains("Quote Header Updated Successfully")){
                 message.setSeverity(FacesMessage.SEVERITY_INFO);
                       fc.addMessage(null, message);   }
        else {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
        }

    }
}
