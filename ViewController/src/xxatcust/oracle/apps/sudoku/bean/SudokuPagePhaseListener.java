package xxatcust.oracle.apps.sudoku.bean;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import oracle.adf.controller.v2.lifecycle.ADFLifecycle;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.share.Environment;
import oracle.adf.share.logging.ADFLogger;

import oracle.apps.fnd.ext.common.AppsRequestWrapper;
import oracle.apps.fnd.ext.common.AppsSessionHelper;
import oracle.apps.fnd.ext.common.CookieStatus;
import oracle.apps.fnd.ext.common.EBiz;
import oracle.apps.fnd.ext.common.Session;

import oracle.apps.fnd.ext.common.State;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jbo.server.DBTransaction;

import xxatcust.oracle.apps.sudoku.model.module.SudokuAMImpl;

public class SudokuPagePhaseListener implements PagePhaseListener {
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(SudokuPagePhaseListener.class);
    String _home_url = null;
    String currentUrlName = null;
    String _logout_url = null;

    public SudokuPagePhaseListener() {
        super();
    }

    public void manageAttributes(SudokuAMImpl amClient) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        Session session = amClient.getAppsSession();
        //   Map ebsAttribs = session.getInfo();
        currentUrlName = amClient.getCurrentUrl();
        _home_url = currentUrlName + "OA.jsp?OAFunc=OAHOMEPAGE#";
        _logout_url = currentUrlName + "OALogout.jsp?menu=Y";
        ExternalContext ectx = fctx.getExternalContext();
        HttpSession httpSession = (HttpSession)ectx.getSession(false);
        httpSession.setAttribute("_home_url", _home_url);
        httpSession.setAttribute("_logout_url", _logout_url);
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        //Comment for local run
       // validateEBSSession(pagePhaseEvent);
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


    private void initializeAppsContext(String respId, String userId,
                                       String applicationId) {
        ApplicationModule am = getAppModule();
        DBTransaction txn = (DBTransaction)am.getTransaction();
        CallableStatement st = null;
        try {

            st =
 txn.createCallableStatement("BEGIN fnd_global.apps_initialize(:1, :2, :3); END;",
                             0);
            st.setString(1, userId);
            st.setString(2, respId);
            st.setString(3, applicationId);
            st.execute();
            st.close();

        } catch (Exception e) {
            _logger.info("Main Exception2===>" + e.getMessage());
        }
    }

    public void logoutEBS() {
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

    public void validateEBSSession(PagePhaseEvent pagePhaseEvent) {
        if (ADFLifecycle.INIT_CONTEXT_ID == pagePhaseEvent.getPhaseId()) {
            EBiz INSTANCE = null;
            Environment env = ADFContext.getCurrent().getEnvironment();
            HttpServletRequest request =
                ((HttpServletRequest)env.getRequest());
            HttpServletResponse response =
                ((HttpServletResponse)env.getResponse());
            HttpSession sessionADF = request.getSession();
            AppsRequestWrapper wrappedRequest = null;
            String applServerID = null;
            Connection EBSconn = null;
            try {
                ApplicationModule am = getAppModule();
                EBSconn = getConnFromDS((ApplicationModuleImpl)am);
                ServletContext servContext =
                    (ServletContext)ADFContext.getCurrent().getEnvironment().getContext();
                _logger.info("servLetContext : " + servContext);
                applServerID = servContext.getInitParameter("APPL_SERVER_ID");
                _logger.info("applServerID: " + applServerID);
                INSTANCE = new EBiz(EBSconn, applServerID);
                wrappedRequest =
                        new AppsRequestWrapper(request, response, EBSconn,
                                               INSTANCE);
                _logger.info("wrappedRequest : " + wrappedRequest);
                oracle.apps.fnd.ext.common.Session sessionEBS =
                    wrappedRequest.getAppsSession(true);
                _logger.info("sessionEBS : " + sessionEBS);
                if (sessionEBS != null) {
                    if (!isEBSSessionValid(sessionEBS)) {
                        _logger.info("EBS Session Not valid ,Logging out ");
                        logoutEBS();
                        return;
                    } else {
                        String userId = sessionEBS.getUserId();
                        _logger.info("UserId : " + userId);
                        String userName = sessionEBS.getUserName();
                        _logger.info("userName : " + userName);
                        String language = wrappedRequest.getLangCode();
                        _logger.info("language : " + language);
                        Map<String, String> info = sessionEBS.getInfo();
                        String respId = info.get("RESPONSIBILITY_ID");
                        _logger.info("respId : " + respId);
                        String respApplId =
                            info.get("RESPONSIBILITY_APPLICATION_ID");
                        _logger.info("respApplId : " + respApplId);
                        String applicationId = info.get("RESP_APPL_ID");
                        _logger.info("applicationId : " + applicationId);
                        String secGrpId = info.get("SECURITY_GROUP_ID");
                        _logger.info("secGrpId : " + secGrpId);
                        String orgId = info.get("ORG_ID");
                        _logger.info("orgId : " + orgId);
                        //Initialize appsContext
                        _logger.info("Initializing Apps Context.....");
                        initializeAppsContext(respId, userId, applicationId);
                        _logger.info("Apps Context Initialized.....");
                        String sessionCookieValue =
                            wrappedRequest.getICXCookieValue();

                        sessionADF.setAttribute("EBS_SESSION_INSTANCE",
                                                INSTANCE);
                        sessionADF.setAttribute("EBS_APPS_SESSION_INSTANCE",
                                                sessionEBS);
                        sessionADF.setAttribute("SessionCookieValue",
                                                sessionCookieValue);
                        sessionADF.setAttribute("UserId", userId);
                        sessionADF.setAttribute("RespId", respId);
                        sessionADF.setAttribute("ApplId", respApplId);
                        sessionADF.setAttribute("UserName", userName);
                        sessionADF.setAttribute("Language", language);
                        sessionADF.setAttribute("SecGrpId", secGrpId);
                        sessionADF.setAttribute("OrgId", orgId);

                        javax.servlet.http.Cookie cookie =
                            wrappedRequest.getICXCookie();
                        String icxcookieName = cookie.getName();
                        String icxcookieValue = cookie.getValue();
                        sessionADF.setAttribute("cookie", cookie);
                        sessionADF.setAttribute("icxcookieName",
                                                icxcookieName);
                        sessionADF.setAttribute("icxcookieValue",
                                                icxcookieValue);
                    }
                }

            } catch (SQLException se) {
                _logger.info("Exception in " + this.getClass().getName() +
                             se.getMessage());
            } catch (Exception e) {
                _logger.info("Exception in " + this.getClass().getName() +
                             e.getMessage());
            } finally {
                try {
                    if (EBSconn != null) {
                        EBSconn.close();
                    }
                } catch (SQLException se) {
                    _logger.info("Exception in " + this.getClass().getName() +
                                 se.getMessage());
                }
            }
        }
    }

    public boolean isEBSSessionValid(oracle.apps.fnd.ext.common.Session sessionEBS) {
        State state = sessionEBS.getCurrentState();
        
        CookieStatus icxSessionStatus = state.getIcxCookieStatus();
        _logger.info("Cookie Status : " + icxSessionStatus);
        if (!icxSessionStatus.equals(CookieStatus.VALID)) {
            return false;
        }
        return true;
    }

}
