package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

public class InputParams {
    private String ruleSetTopLevelChoice ;
    private String ruleSetSecondLevelChoice ;
    private String importSource ;//Budgetory Quote, Formal Quote, XML and other values
    private String quoteNumber;
    private Boolean reuseQuote;//true if "Re-use the existing Quote ID, if possible" is selected 
    private Boolean copyReferenceConfiguration ;
    public InputParams() {
        super();
    }

    public void setRuleSetTopLevelChoice(String ruleSetTopLevelChoice) {
        this.ruleSetTopLevelChoice = ruleSetTopLevelChoice;
    }

    public String getRuleSetTopLevelChoice() {
        return ruleSetTopLevelChoice;
    }

    public void setRuleSetSecondLevelChoice(String ruleSetSecondLevelChoice) {
        this.ruleSetSecondLevelChoice = ruleSetSecondLevelChoice;
    }

    public String getRuleSetSecondLevelChoice() {
        return ruleSetSecondLevelChoice;
    }

    public void setImportSource(String importSource) {
        this.importSource = importSource;
    }

    public String getImportSource() {
        return importSource;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setReuseQuote(Boolean reuseQuote) {
        this.reuseQuote = reuseQuote;
    }

    public Boolean getReuseQuote() {
        return reuseQuote;
    }

    public void setCopyReferenceConfiguration(Boolean copyReferenceConfiguration) {
        this.copyReferenceConfiguration = copyReferenceConfiguration;
    }

    public Boolean getCopyReferenceConfiguration() {
        return copyReferenceConfiguration;
    }
}
