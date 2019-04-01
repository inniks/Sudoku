package xxatcust.oracle.apps.sudoku.bean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;

public class ImportSource {
    private String sourceSelected ;
    private RichInputText budgetQuote;
    private RichInputText formalQuote;

    public ImportSource() {
    }

    public void importSrcSelected(ValueChangeEvent valueChangeEvent) {
        String selectedVal = null;
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        BindingContainer bindings =
            (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding listBinding =
            (JUCtrlListBinding)bindings.get("ImportSrcMeaning");
        Row selectedRow = (Row)listBinding.getSelectedValue();
        if (selectedRow != null) {
            selectedVal = (String)selectedRow.getAttribute("ImpSrcCode");
            setSourceSelected(selectedVal);
            System.out.println("Selected Value " + selectedVal);
        }

    }

    public void copyRefChanged(ValueChangeEvent valueChangeEvent) {
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        BindingContainer bindings =
            (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding listBinding =
            (JUCtrlListBinding)bindings.get("CopyRefConfig");
        Object selectedValue = listBinding.getSelectedValue();
        System.out.println(selectedValue.getClass());
    }

    public void reuseQuoteChange(ValueChangeEvent valueChangeEvent) {
        String selectedVal = null;
        UIComponent uiComp = (UIComponent)valueChangeEvent.getSource();
        uiComp.processUpdates(FacesContext.getCurrentInstance());
        BindingContainer bindings =
            (BindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        JUCtrlListBinding listBinding =
            (JUCtrlListBinding)bindings.get("ReuseQuote");
        Row selectedRow = (Row)listBinding.getSelectedValue();
        if (selectedRow != null) {
            selectedVal = (String)selectedRow.getAttribute("ReuseCode");
            System.out.println("Selected Value " + selectedVal);
        }
    }

    public void setSourceSelected(String sourceSelected) {
        this.sourceSelected = sourceSelected;
    }

    public String getSourceSelected() {
        return sourceSelected;
    }

    public void setBudgetQuote(RichInputText budgetQuote) {
        this.budgetQuote = budgetQuote;
    }

    public RichInputText getBudgetQuote() {
        return budgetQuote;
    }

    public void setFormalQuote(RichInputText formalQuote) {
        this.formalQuote = formalQuote;
    }

    public RichInputText getFormalQuote() {
        return formalQuote;
    }

    public void importConfiguration(ActionEvent actionEvent) {
        UIComponent uiComponent = (UIComponent)actionEvent.getSource();
        uiComponent.processUpdates(FacesContext.getCurrentInstance());
        ADFUtils.setSessionScopeValue("refreshImport", "Y");
    }
}
