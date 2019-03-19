package xxatcust.oracle.apps.sudoku.bean;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;
import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.binding.BindingContainer;

import oracle.jbo.AttributeDef;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.common.JboCompOper;
import oracle.jbo.common.ListBindingDef;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class LOVPartialSearchClasss {
    public LOVPartialSearchClasss() {
        super();
    }

    public void launchPopup(LaunchPopupEvent launchPopupEvent) {
        String currentColumnName = null;
        String valueExpression = null;
        JUCtrlListBinding ctrlListBinding = null;
        ViewObject baseViewObject = null;
        AttributeDef lovAttributeDef = null;
        AttributeDef lovlistAttributeDef = null;
        ListBindingDef listBindingDef = null;
        String[] listAttributeNames = null;
        ListOfValuesModel listOfValuesModel = null;
        ViewCriteria viewCriteria = null;
        Object submittedValue = null;
        ViewCriteriaRow viewCriteriaRow = null;
        RowSet rowSet = null;
        Row listRow = null;
        Row baseViewObjectRow = null;
        if (!launchPopupEvent.isLaunchPopup()) {
            return;
        }
        submittedValue = launchPopupEvent.getSubmittedValue();
      //  if (submittedValue != null && !submittedValue.equals("")) {

            BindingContext bindingContext = BindingContext.getCurrent();
            BindingContainer bindingContainter =
                bindingContext.getCurrentBindingsEntry();
            RichInputListOfValues inputListOfValues =
                (RichInputListOfValues)launchPopupEvent.getComponent();
            if (inputListOfValues != null) {

                // below code gets the value expression for the current column i.e in our case #{bindings.SiteName.inputValue}
                valueExpression =
                        inputListOfValues.getValueExpression(inputListOfValues.VALUE_KEY.getName()).toString();
                if (valueExpression != null) {
                    // if the column is in the af table then the value will be #{row.bindings.SiteName.inputValue} so we are replacing row.
                    valueExpression =
                            StringUtils.replace(valueExpression, "row.", "");
//                    if(valueExpression!=null && valueExpression.equalsIgnoreCase("ValueExpression[#{sessionScope.sessionWideSiteName}]")){
//                        
//                    }
                    System.out.println(valueExpression);
                    currentColumnName =
                            valueExpression.substring(valueExpression.indexOf(".") +
                                                      1,
                                                      valueExpression.lastIndexOf("."));
                    if (currentColumnName != null) {
                        ctrlListBinding =
                                (JUCtrlListBinding)bindingContainter.getControlBinding(currentColumnName);
                        if (ctrlListBinding != null) {
                            baseViewObject =
                                    ctrlListBinding.getIteratorBinding().getViewObject();
                            if (baseViewObject != null) {
                                baseViewObjectRow =
                                        baseViewObject.getCurrentRow();
                                lovAttributeDef =
                                        baseViewObject.findAttributeDef(currentColumnName);
                                if (lovAttributeDef != null) {
                                    listBindingDef =
                                            lovAttributeDef.getListBindingDef();
                                    if (listBindingDef != null) {
                                        listAttributeNames =
                                                listBindingDef.getListAttrNames();
                                        if (listAttributeNames != null &&
                                            listAttributeNames.length > 0) {
                                            listOfValuesModel =
                                                    inputListOfValues.getModel();
                                            if (listOfValuesModel != null) {
                                              DCIteratorBinding iterBinding =  ctrlListBinding.getListIterBinding();
                                                viewCriteria =
                                                        iterBinding.getViewObject().getViewCriteriaManager().getViewCriteria(listBindingDef.getDisplayCriteriaName());
                                                if (viewCriteria != null) {
                                                    viewCriteriaRow =
                                                            (ViewCriteriaRow)viewCriteria.getRowAtRangeIndex(0);
                                                    if (viewCriteriaRow !=
                                                        null) {
                                                        lovlistAttributeDef =
                                                                viewCriteriaRow.getStructureDef().findAttributeDef(listAttributeNames[0]);
                                                        if (lovAttributeDef !=
                                                            null) {
                                                            if (lovAttributeDef.getSQLType() ==
                                                                OracleTypes.NUMBER) {
                                                                viewCriteriaRow.setAttribute(listAttributeNames[0],
                                                                                             submittedValue);
                                                                viewCriteriaRow.getCriteriaItem(listAttributeNames[0]).setOperator(JboCompOper.OPER_EQ);
                                                            } else if (lovAttributeDef.getSQLType() ==
                                                                       OracleTypes.VARCHAR) {
                                                                String a =
                                                                    "%" +
                                                                    submittedValue +
                                                                    "%";
                                                                viewCriteriaRow.setAttribute(listAttributeNames[0],
                                                                                             a); /*submittedValue+"%"*/
                                                                viewCriteriaRow.getCriteriaItem(listAttributeNames[0]).setOperator(JboCompOper.OPER_LIKE);
                                                            }
                                                            listOfValuesModel.performQuery(listOfValuesModel.getQueryDescriptor());
                                                            if (ctrlListBinding.getListIterBinding().getRowSetIterator() !=
                                                                null && ctrlListBinding.getListIterBinding().getRowSetIterator().getFetchedRowCount()>0  /*&&
                                                                ctrlListBinding.getListIterBinding().getRowSetIterator().get >
                                                                0*/) {
                                                                rowSet =
                                                                        ctrlListBinding.getListIterBinding().getRowSetIterator().getRowSet();
                                                                listRow =
                                                                        rowSet.getRowAtRangeIndex(0);
                                                                if (listRow !=
                                                                    null &&
                                                                    listRow.getAttribute(listAttributeNames[0]).equals(submittedValue)) {
                                                                    if (baseViewObjectRow !=
                                                                        null) {
                                                                        baseViewObjectRow.setAttribute(currentColumnName,
                                                                                                       submittedValue);
                                                                        baseViewObject.setCurrentRow(baseViewObjectRow);
                                                                    }
                                                                    RowKeySetImpl rowKeySet =
                                                                        new RowKeySetImpl();
                                                                    List list =
                                                                        new ArrayList();
                                                                    list.add(listRow.getKey());
                                                                    launchPopupEvent.setLaunchPopup(false);
                                                                    launchPopupEvent.queue();
                                                                    inputListOfValues.queueEvent(new ReturnPopupEvent(inputListOfValues,
                                                                                                                      rowKeySet));
                                                                    AdfFacesContext.getCurrentInstance().addPartialTarget(inputListOfValues);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

       // }
    }
}
