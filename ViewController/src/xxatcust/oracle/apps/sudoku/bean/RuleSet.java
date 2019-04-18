package xxatcust.oracle.apps.sudoku.bean;

import java.util.HashMap;

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
        DCIteratorBinding iter = ADFUtils.findIterator("RuleSetVO1Iterator");
        if (iter != null) {
            Row currRow = iter.getCurrentRow();
            if (currRow != null) {
                currRow.setAttribute("SecondLevelMeaning",
                                     null); // reset the second level choice
                String topLevelCode =
                    (String)currRow.getAttribute("TopLevelCode");
                System.out.println("Top Level Code " + topLevelCode);
                HashMap inputParamsMap =
                    (HashMap)ADFUtils.getSessionScopeValue("inputParamsMap");
                if (inputParamsMap == null) {
                    inputParamsMap = new HashMap();
                }
                inputParamsMap.put("ruleSetTop", topLevelCode);
                ADFUtils.setSessionScopeValue("inputParamsMap",
                                              inputParamsMap);
            }
        }
    }

    public void secLevelValSelected(ValueChangeEvent valueChangeEvent) {
        UIComponent ui = (UIComponent)valueChangeEvent.getSource();
        ui.processUpdates(FacesContext.getCurrentInstance());
        DCIteratorBinding iter = ADFUtils.findIterator("RuleSetVO1Iterator");
        if (iter != null) {
            Row currRw = iter.getCurrentRow();
            if (currRw != null) {
                String secondLevelCode =
                    (String)currRw.getAttribute("SecondLevelCode");
                System.out.println("Second Level Code " + secondLevelCode);
                HashMap inputParamsMap =
                    (HashMap)ADFUtils.getSessionScopeValue("inputParamsMap");
                if (inputParamsMap == null) {
                    inputParamsMap = new HashMap();
                }
                inputParamsMap.put("ruleSetSecond", secondLevelCode);
                ADFUtils.setSessionScopeValue("inputParamsMap",
                                              inputParamsMap);

            }
        }
    }
}
