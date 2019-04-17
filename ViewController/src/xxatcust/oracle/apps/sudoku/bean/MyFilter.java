package xxatcust.oracle.apps.sudoku.bean;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilter implements Filter {
    private FilterConfig fc = null;

    public MyFilter() {
        super();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        fc = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException,
                                                         ServletException {
        String reqSessionID =
            ((HttpServletRequest)servletRequest).getRequestedSessionId();
        String currentSessionID =
            ((HttpServletRequest)servletRequest).getSession().getId();
        String requestURI =
            ((HttpServletRequest)servletRequest).getRequestURI();
        boolean sameSession = currentSessionID.equalsIgnoreCase(reqSessionID);
        if (!sameSession && reqSessionID != null) {
            ((HttpServletResponse)servletResponse).sendRedirect(requestURI);
            System.out.println("Session is null");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
