package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

public class SessionDetails {
    private String userId;
    private String applicationId;
    private String respId;
    private String modelId;
    private String publicationId;
    private String masterOrgID;
    private String modelName;
    private String priceListID;
    private Double conversionRateToUSD;
    private String configHdrId;
    private String configRevNbr;
    
    public SessionDetails() {
        super();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setRespId(String respId) {
        this.respId = respId;
    }

    public String getRespId() {
        return respId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setMasterOrgID(String masterOrgID) {
        this.masterOrgID = masterOrgID;
    }

    public String getMasterOrgID() {
        return masterOrgID;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setPriceListID(String priceListID) {
        this.priceListID = priceListID;
    }

    public String getPriceListID() {
        return priceListID;
    }

    public void setConversionRateToUSD(Double conversionRateToUSD) {
        this.conversionRateToUSD = conversionRateToUSD;
    }

    public Double getConversionRateToUSD() {
        return conversionRateToUSD;
    }

    public void setConfigHdrId(String configHdrId) {
        this.configHdrId = configHdrId;
    }

    public String getConfigHdrId() {
        return configHdrId;
    }

    public void setConfigRevNbr(String configRevNbr) {
        this.configRevNbr = configRevNbr;
    }

    public String getConfigRevNbr() {
        return configRevNbr;
    }
}
