package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;

public class RuleSet {
    public RuleSet() {
    }

    public void topLevelChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent ui = (UIComponent)valueChangeEvent.getSource();
        ui.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding iter = ADFUtils.findIterator("RuleSetVO1Iterator") ;
        if(iter!=null){
            Row currRow = iter.getCurrentRow() ;
            if(currRow!=null){
                currRow.setAttribute("SecondLevelMeaning", null); // reset the second level choice
            }
        }
    }
}
