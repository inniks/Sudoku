package xxatcust.oracle.apps.sudoku.bean;

import java.io.Serializable;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class PabelTabHandler implements Serializable {
    private RichShowDetailItem viewReferenceTab;

    public PabelTabHandler() {
    }

    public void setViewReferenceTab(RichShowDetailItem viewReferenceTab) {
        this.viewReferenceTab = viewReferenceTab;
    }

    public RichShowDetailItem getViewReferenceTab() {
        return viewReferenceTab;
    }

    public void viewReferenceDisclosed(DisclosureEvent disclosureEvent) {
        XMLImportPageBean importBean = new XMLImportPageBean() ;
        importBean.refreshViewReferenceTab();
    }
}
