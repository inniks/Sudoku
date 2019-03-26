package xxatcust.oracle.apps.sudoku.bean;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.apps.fnd.ext.common.AppsRequestWrapper;
import oracle.apps.fnd.ext.common.AppsSessionHelper;
import oracle.apps.fnd.ext.common.EBiz;
import oracle.apps.fnd.ext.common.Session;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.server.ApplicationModuleImpl;

public class TemplateBean {
    public TemplateBean() {
        super();
    }

    private long taskFlowParam = 0;
    private String InitSesnScope = "";
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(TemplateBean.class);

    public long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public void setTaskFlowParam(long taskFlowParam) {
        this.taskFlowParam = taskFlowParam;
    }

    public long getTaskFlowParam() {
        return taskFlowParam;
    }

    public void logout(ActionEvent actionEvent) {
        System.out.println("Logging out of EBS....");
        _logger.info("Logging out the user from EBS");

        FacesContext fctx = FacesContext.getCurrentInstance();
        HttpServletRequest request =
            (HttpServletRequest)fctx.getExternalContext().getRequest();
        HttpServletResponse response =
            (HttpServletResponse)fctx.getExternalContext().getResponse();
        //invalidate ICX session & HTTP session
        AppsRequestWrapper wrappedRequest = null;
        String logoutEbsUrl = null;
        try {
            ApplicationModule am = getAppModule();

            _logger.info("am==>" + am);

            Connection EBSconn = getConnFromDS((ApplicationModuleImpl)am);
            ServletContext servContext =
                (ServletContext)ADFContext.getCurrent().getEnvironment().getContext();
            String applServerID =
                servContext.getInitParameter("APPL_SERVER_ID");
            _logger.info("applServerID==>" + applServerID);
            EBiz instance = new EBiz(EBSconn, applServerID);
            wrappedRequest =
                    new AppsRequestWrapper(request, response, EBSconn, instance);
            logoutEbsUrl =
                    wrappedRequest.getEbizInstance().getAppsServletAgent();
            logoutEbsUrl = logoutEbsUrl + "OALogout.jsp?menu=Y";
            _logger.info("logoutEbsUrl = " + logoutEbsUrl);
            Session sessionEBS = wrappedRequest.getAppsSession();
            //logout only if it is present
            if (sessionEBS != null) {
                AppsSessionHelper helper =
                    new AppsSessionHelper(wrappedRequest.getEbizInstance());
                helper.destroyAppsSession(wrappedRequest.getAppsSession(),
                                          wrappedRequest, response);
            }
            ExternalContext ectx =
                FacesContext.getCurrentInstance().getExternalContext();
            HttpSession sessionHttp = (HttpSession)ectx.getSession(false);
            if (sessionHttp != null) {
                try {
                    sessionHttp.invalidate();
                } catch (IllegalStateException ex) {
                    _logger.severe("Error - HttpSession already invalidated,",
                                   ex);
                }
            }
            response.sendRedirect(logoutEbsUrl);
            fctx.responseComplete();
        } catch (Exception ex) {
            _logger.severe("Error , ", ex);
            throw (new JboException(ex));
        }
    }

    public void redirectToHome(ActionEvent actionEvent) {
        System.out.println("Redirecting to EBS home....");
    }

    public static ApplicationModule getAppModule() {
        BindingContext bctx = BindingContext.getCurrent();
        DCDataControl dc = bctx.findDataControl("SudokuAMDataControl");
        ApplicationModule am = (ApplicationModule)dc.getDataProvider();

        return am;
    }

    public Connection getConnFromDS(ApplicationModuleImpl am) {
        Statement st = am.getDBTransaction().createStatement(0);
        Connection conn = null;
        try {
            conn = st.getConnection();
            st.close();
        } catch (SQLException ex) {
            _logger.severe("Error , ", ex);
            throw (new JboException(ex));
        }
        return conn;
    }
}
