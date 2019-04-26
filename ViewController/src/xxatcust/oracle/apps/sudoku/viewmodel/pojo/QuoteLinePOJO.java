package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;

public class QuoteLinePOJO {
    public QuoteLinePOJO() {
        super();
    }
    
    ArrayList<ConfiguratorNodePOJO> items;
    String lineNumber;
    String quoteLineId; // this is null in create mode.
    String itemName;
    String configHrdId; 
    String configRevNum;
    String quoteNumber;
    String operationCode; // CREATE when adding new line to quote, UPDATE when updating existing line, READONLY for reference configuratino
    String itemTypeCode; // MDL for adding model item, SRV for adding service item

    public void setItems(ArrayList<ConfiguratorNodePOJO> items) {
        this.items = items;
    }

    public ArrayList<ConfiguratorNodePOJO> getItems() {
        return items;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setQuoteLineId(String quoteLineId) {
        this.quoteLineId = quoteLineId;
    }

    public String getQuoteLineId() {
        return quoteLineId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setConfigHrdId(String configHrdId) {
        this.configHrdId = configHrdId;
    }

    public String getConfigHrdId() {
        return configHrdId;
    }

    public void setConfigRevNum(String configRevNum) {
        this.configRevNum = configRevNum;
    }

    public String getConfigRevNum() {
        return configRevNum;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setItemTypeCode(String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeCode() {
        return itemTypeCode;
    }
}
