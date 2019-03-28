package xxatcust.oracle.apps.sudoku.model.module.client;

import oracle.jbo.client.remote.ApplicationModuleImpl;

import xxatcust.oracle.apps.sudoku.model.module.common.SudokuAM;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 28 16:56:55 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SudokuAMClient extends ApplicationModuleImpl implements SudokuAM {
    /**
     * This is the default constructor (do not remove).
     */
    public SudokuAMClient() {
    }

    public void clearQuoteFields() {
        Object _ret =
            this.riInvokeExportedMethod(this,"clearQuoteFields",null,null);
        return;
    }

    public void searchQuote() {
        Object _ret = this.riInvokeExportedMethod(this,"searchQuote",null,null);
        return;
    }

    public void initQuoteSearch() {
        Object _ret =
            this.riInvokeExportedMethod(this,"initQuoteSearch",null,null);
        return;
    }

    public void getQuoteCustmerAddress() {
        Object _ret =
            this.riInvokeExportedMethod(this,"getQuoteCustmerAddress",null,null);
        return;
    }

    public String callQuoteAPI() {
        Object _ret = this.riInvokeExportedMethod(this,"callQuoteAPI",null,null);
        return (String)_ret;
    }
}