package xxatcust.oracle.apps.sudoku.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class TargetConfiguration {
    private RichOutputText pageInitText;
    ChildPropertyTreeModel categoryTree;
    private RichInputText userInputFileName;
    private RichPopup downloadPopup;

    public TargetConfiguration() {
    }

    public void setPageInitText(RichOutputText pageInitText) {
        this.pageInitText = pageInitText;
    }

    public RichOutputText getPageInitText() {
        return pageInitText;
    }

    public void setCategoryTree(ChildPropertyTreeModel categoryTree) {
        this.categoryTree = categoryTree;
    }

    public ChildPropertyTreeModel getCategoryTree() {
        categoryTree =
                (ChildPropertyTreeModel)ADFUtils.getSessionScopeValue("categoryTree");
        return categoryTree;
    }

    public void initTargetConfiguration() {
        System.out.println("Initializing target configuration....");
    }

    public void setUserInputFileName(RichInputText userInputFileName) {
        this.userInputFileName = userInputFileName;
    }

    public RichInputText getUserInputFileName() {
        return userInputFileName;
    }

    public void downloadAction(FacesContext facesContext,
                               OutputStream outputStream) {
        System.out.println("Download Listener Fired");
        try {
            File file = new File("D://Projects//Advantest//JsonResponse/exportTarget.xml");
            FileInputStream fis;
            byte[] b;
            fis = new FileInputStream(file);

            int n;
            while ((n = fis.available()) > 0) {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
            outputStream.flush();
        } catch (FileNotFoundException fnfe) {
            // TODO: Add catch code
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            // TODO: Add catch code
            ioe.printStackTrace();
            } finally{
                downloadPopup.cancel();
            }
        
    }

    public void cancelDownloadPopup(ActionEvent actionEvent) {
        downloadPopup.cancel();
    }

    public void setDownloadPopup(RichPopup downloadPopup) {
        this.downloadPopup = downloadPopup;
    }

    public RichPopup getDownloadPopup() {
        return downloadPopup;
    }

    public void openDownloadPopup(ActionEvent actionEvent) {
        Object parentObj = ADFUtils.getSessionScopeValue("parentObject");
        if (parentObj != null) {
            V93kQuote v93k = (V93kQuote)parentObj;
            JaxbParser.jaxbObjectToXML(v93k);
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        downloadPopup.show(hints);
    }
    
    public String getUserFileName(){
        if(userInputFileName!=null){
            return (String)userInputFileName.getValue();
        }
        else return "ExportedXML" ;
    }
}
