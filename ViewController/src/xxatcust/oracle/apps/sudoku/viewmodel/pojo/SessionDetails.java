package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

public class SessionDetails {
    private String userId;
    private String applicationId;
    private String respId;
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
}
