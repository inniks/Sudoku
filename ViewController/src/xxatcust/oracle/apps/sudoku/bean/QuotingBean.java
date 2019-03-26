package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;


public class QuotingBean {
    public QuotingBean() {
        super();
    }
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
        

    public void saveQuoteDetails(ActionEvent actionEvent) {
        String msg = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        OperationBinding operationBinding = getBindings().getOperationBinding("callQuoteAPI");
            if(operationBinding.getErrors().size()==0)
        msg = (String)operationBinding.execute();
        FacesMessage message = new FacesMessage(msg);  
        if(msg.contains("<html><body>")){
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                          fc.addMessage(null, message);
                    }
        else{        
         message.setSeverity(FacesMessage.SEVERITY_INFO);
               fc.addMessage(null, message);   }
    }

    public void clearFields(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("clearQuoteFields");
        if(ob.getErrors().size()==0){
            ob.execute();
            }
    }

    public void searchQuote(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("searchQuote");
        //        if(ob.getErrors().size()==0){
            ob.execute();
    }

    public void custmoerSupportVCE(ValueChangeEvent valueChangeEvent) {
        System.out.println(valueChangeEvent.getNewValue());
        if(valueChangeEvent.getNewValue()!=valueChangeEvent.getOldValue() &&valueChangeEvent.getNewValue()!=null ){
        OperationBinding ob = getBindings().getOperationBinding("getFaxNum");
//        if(ob.getErrors().size()==0){
            ob.execute();
            }
        }
  
    public void custNameVCE(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=valueChangeEvent.getOldValue() &&valueChangeEvent.getNewValue()!=null ){
        OperationBinding ob = getBindings().getOperationBinding("getQuoteCustmerAddress");
//                if(ob.getErrors().size()==0){
            ob.execute();
            }
    }

    public void custNameRPL(ReturnPopupEvent returnPopupEvent) {
        if(returnPopupEvent!=null ){
            System.out.println("rop:"+returnPopupEvent);
        OperationBinding ob = getBindings().getOperationBinding("getQuoteCustmerAddress");
        //                if(ob.getErrors().size()==0){
            ob.execute();
            }
    }
}
