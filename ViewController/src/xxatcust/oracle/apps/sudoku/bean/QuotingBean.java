package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


public class QuotingBean {
    public QuotingBean() {
        super();
    }
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
        

    public void saveQuoteDetails(ActionEvent actionEvent) {
        OperationBinding operationBinding = getBindings().getOperationBinding("callQuoteAPI");
            if(operationBinding.getErrors().size()==0)
        operationBinding.execute();
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
   
}
