package xxatcust.oracle.apps.sudoku.bean;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.apps.fnd.ext.common.AppsRequestWrapper;
import oracle.apps.fnd.ext.common.AppsSessionHelper;
import oracle.apps.fnd.ext.common.CookieStatus;
import oracle.apps.fnd.ext.common.EBiz;
import oracle.apps.fnd.ext.common.Session;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jbo.server.DBTransaction;

import xxatcust.oracle.apps.sudoku.model.module.SudokuAMImpl;

public class SudokuPagePhaseListener implements PagePhaseListener {
    private static ADFLogger _logger =
            ADFLogger.createADFLogger(SudokuPagePhaseListener.class);
    String _home_url=null;
    String currentUrlName=null;
    String _logout_url=null;
   
    public SudokuPagePhaseListener() {
        super();
    }
    public void manageAttributes(SudokuAMImpl amClient) 
    {
        FacesContext fctx = FacesContext.getCurrentInstance();
        Session session = amClient.getAppsSession();
     //   Map ebsAttribs = session.getInfo();
        currentUrlName=amClient.getCurrentUrl();
        _home_url = currentUrlName+"OA.jsp?OAFunc=OAHOMEPAGE#";
        _logout_url = currentUrlName+"OALogout.jsp?menu=Y";
        ExternalContext ectx = fctx.getExternalContext();
        HttpSession httpSession = (HttpSession)ectx.getSession(false);
        httpSession.setAttribute("_home_url", _home_url);
        httpSession.setAttribute("_logout_url", _logout_url);
    }
    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) 
    {
//        if (pagePhaseEvent.getPhaseId() == Lifecycle.PREPARE_RENDER_ID ||
//            pagePhaseEvent.getPhaseId() == Lifecycle.INIT_CONTEXT_ID) 
//        {
//            String agent = null;
//            _logger.info("In before Phase");          
//            FacesContext fctx = FacesContext.getCurrentInstance();
//            HttpServletRequest request =
//                (HttpServletRequest)fctx.getExternalContext().getRequest();
//            HttpServletResponse response =
//                (HttpServletResponse)fctx.getExternalContext().getResponse();
//            CookieStatus icxCookieStatus = null;
//            String currentUser = null;
//            String currentUserId;
//            try {
//                ApplicationModule am = getAppModule();
//                SudokuAMImpl amClient = (SudokuAMImpl)am; 
//                manageAttributes(amClient);
//                Map map = ADFContext.getCurrent().getSessionScope();
//                _logger.info("am==>" + am);                  
//                Connection EBSconn = getConnFromDS((ApplicationModuleImpl)am);
//                ServletContext servContext =
//                    (ServletContext)ADFContext.getCurrent().getEnvironment().getContext();
//                String applServerID =
//                    servContext.getInitParameter("APPL_SERVER_ID");                
//                _logger.info("applServerID==>" + applServerID);
//                EBiz instance = new EBiz(EBSconn, applServerID);
//                _logger.info("instance==>" + instance);
//                AppsRequestWrapper wrappedRequest =
//                    new AppsRequestWrapper(request, response, EBSconn,
//                                           instance);
//                _logger.info("wrappedRequest==>" + wrappedRequest);
//                map.put("applServerID", applServerID);
//                map.put("instance", instance);
//                map.put("wrappedRequest", wrappedRequest);
//                Session session = wrappedRequest.getAppsSession();
//                _logger.info("session==>" + session);
//                icxCookieStatus =
//                        session.getCurrentState().getIcxCookieStatus();
//                _logger.info("icxCookieStatus==>" + icxCookieStatus);
//                agent = wrappedRequest.getEbizInstance().getAppsServletAgent();
//                _logger.info("icxCookieStatus==>" + icxCookieStatus);
//                currentUser = session.getUserName();
//                currentUserId = session.getUserId();
//                _logger.info("currentUser==>" + currentUser);
//                map.put("currentUser", currentUser);
//                _logger.info("currentUserId==>" + currentUserId);
//                _logger.info("ADFContext.getCurrent().getSecurityContext().getUserName()==>" +
//                 ADFContext.getCurrent().getSecurityContext().getUserName());               
//                map.put("LoggedInUser", currentUser);
//                map.put("LoggedInUserId", currentUserId);
//                if (!icxCookieStatus.equals(CookieStatus.VALID)) {
//                    logoutEBS();
//                    return;
//                } else if (session != null) {
//                    FacesContext.getCurrentInstance().getViewRoot().setLocale(session.getLocale());
//                    Map ebsSessionMap = session.getInfo();
//                    String respId =
//                        (String)ebsSessionMap.get("RESPONSIBILITY_ID");
//                    String applicationId =
//                        (String)ebsSessionMap.get("RESP_APPL_ID");
//                    String orgId = (String)ebsSessionMap.get("ORG_ID");
//                    String userId = (String)ebsSessionMap.get("USER_ID");
//                    initializeAppsContext(respId, userId, applicationId);
//                    _logger.info("respId==>" + respId);
//                    _logger.info("applicationId==>" + applicationId);
//                    _logger.info("orgId==>" + orgId);
//                    _logger.info("userId==>" + userId);
//                }
//            } catch (Exception ob) {
//                Map map = ADFContext.getCurrent().getSessionScope();
//                map.put("LoggedInUser",
//                        ADFContext.getCurrent().getSecurityContext().getUserName());
//                ob.printStackTrace();
//                return;
//            }
//            System.out.println("Out before phase");
//        }
    }
    
    
    
    public static ApplicationModule getAppModule() 
    {
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

            st = txn.createCallableStatement("BEGIN fnd_global.apps_initialize(:1, :2, :3); END;",
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


            //invalidate ICX session & HTTP session
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
            //wrappedRequest.getRequestDispatcher("/WEB-INF/logout.jsp").forward(wrappedRequest, response);
            response.sendRedirect(logoutEbsUrl);
            fctx.responseComplete();
        } catch (Exception ex) {
            _logger.severe("Error , ", ex);
            throw (new JboException(ex));
        }
    }
    
}
