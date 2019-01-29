package xxatcust.oracle.apps.sudoku.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;

import oracle.adf.view.rich.context.AdfFacesContext;

public class ConfiguratorUtils {
    public ConfiguratorUtils() {
        super();
    }

    public static String callConfiguratorServlet(String jsonStr) {
        // Add event code here...
        StringBuffer strBuf = new StringBuffer();
        try {
            URL url =
                new URL("http://usdcnvpthap.adv.advantest.com:8000/OA_HTML/configurator/XXATSudokoCzServlet?modelId=21412791&closeConfiguration=NO");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            // inform the connection that we will send output and accept input
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setConnectTimeout(5000);
            con.setRequestProperty("Content-Type",
                                   "application/json; charset=UTF-8");
            // Don't use a cached version of URL connection.
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);
            con.setRequestMethod("POST");
            PrintWriter outWriter = new PrintWriter(con.getOutputStream());
            outWriter.close();
            OutputStream os = con.getOutputStream();
            os.write(jsonStr.getBytes("UTF-8"));
            InputStream input = con.getInputStream();
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(input));
            } catch (Exception e) {
                e.printStackTrace();
            }


            String inputLine = null;
            
            try {
                while ((inputLine = in.readLine()) != null) {
                    strBuf.append("" + inputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String configIdStr = strBuf.toString();

            String configId = "";
            String revision = "";

            if (configIdStr != null && !"".equals(configIdStr)) {
                configIdStr = configIdStr.replace("config hrd Id =", "");

                configIdStr = configIdStr.replace(" config rev nbr ", "");

                revision =
                        configIdStr.substring(configIdStr.indexOf("=") + 1, configIdStr.length());

                configId = configIdStr.replace("=" + revision, "");
            }

            System.out.println("print first button press configId" + configId);

            System.out.println("print first button press revision" + revision);


            AdfFacesContext context = AdfFacesContext.getCurrentInstance();
            Map pageFlowScope = context.getPageFlowScope();
            pageFlowScope.put("XXTESTMSG", strBuf.toString());
            pageFlowScope.put("XXCONFIGID", configId);
            pageFlowScope.put("XXREV", revision);

            System.out.println("Response from servlet::: " +
                               strBuf.toString());

        } catch (Exception fnfe) {
            fnfe.printStackTrace();
        }
        return strBuf.toString() ;
    }


}
