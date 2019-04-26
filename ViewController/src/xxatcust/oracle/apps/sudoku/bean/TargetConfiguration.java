package xxatcust.oracle.apps.sudoku.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

import xxatcust.oracle.apps.sudoku.util.ADFUtils;
import xxatcust.oracle.apps.sudoku.util.JaxbParser;
import xxatcust.oracle.apps.sudoku.util.NodeComparator;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.ConfiguratorNodePOJO;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.NodeCategory;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.QuoteLinePOJO;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;


public class TargetConfiguration {
    private RichOutputText pageInitText;
    ChildPropertyTreeModel categoryTree;
    private RichInputText userInputFileName;
    private RichPopup downloadPopup;
    List<ConfiguratorNodePOJO> allNodes;
    V93kQuote v93Obj;
    private ArrayList<NodeCategory> root;
    ChildPropertyTreeModel categroryTreeLineTwo;
    private RichPopup errPopup;
    private RichOutputFormatted errorText;
    private RichOutputText quoteTotal;

    public TargetConfiguration() {
    }

    public void setPageInitText(RichOutputText pageInitText) {
        this.pageInitText = pageInitText;
    }

    public RichOutputText getPageInitText() {
        return pageInitText;
    }

    public ChildPropertyTreeModel getCategoryTree() {

        try {
            StringBuilder errMessage = new StringBuilder("Error");
            String refreshImport =
                (String)ADFUtils.getSessionScopeValue("refreshImport");
            Object parentObj = ADFUtils.getSessionScopeValue("parentObject");
            String impSrcChanged =
                (String)ADFUtils.getSessionScopeValue("ImpSrcChanged");
            if (impSrcChanged != null && impSrcChanged.equalsIgnoreCase("Y")) {
                categoryTree = null;
                //quoteTotal.setValue(null);
            }
            if (categoryTree == null && refreshImport != null &&
                refreshImport.equalsIgnoreCase("Y") && parentObj != null) {
                if (parentObj != null) {
                    V93kQuote obj = (V93kQuote)parentObj;
                    //Check if no exceptions from configurator
                    if (obj.getExceptionMap() != null) {
                        TreeMap<String, ArrayList<String>> exceptionMap =
                            obj.getExceptionMap().getErrorList();
                        List<String> errorMessages =
                            obj.getExceptionMap().getErrorsMessages();

                        if (exceptionMap != null && exceptionMap.size() > 0) {

                            for (Map.Entry<String, ArrayList<String>> entry :
                                 exceptionMap.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<String> value = entry.getValue();
                                for (String str : value) {
                                    errMessage.append(str);
                                    //errMessage.append(str) ;
                                }
                            }

                            // errMessage.append("</body></html>");

                        }
                        if (errorMessages != null &&
                            errorMessages.size() > 0) {
                            for (String str : errorMessages) {
                                errMessage.append(str);
                                //errMessage.append(str) ;
                            }
                            //errMessage.append("</body></html>");
                        }
                        //  errorText.setValue(errMessage.toString());
                        if (errMessage != null &&
                            !errMessage.toString().equalsIgnoreCase("Error")) {
                            // display error in popup
                        }
                    }

                    if (errMessage != null &&
                        errMessage.toString().equalsIgnoreCase("Error")) {
                        Double sumQuoteTotal = new Double(0);
                        List<String> catList = new ArrayList<String>();
                        List<String> distinctList = new ArrayList<String>();
                        List<ConfiguratorNodePOJO> allNodesList =
                            getAllNodes("1");
                        List<ConfiguratorNodePOJO> secondLineNodes =
                            getAllNodes("2");
                        // obj.getNodeCollection();
                        HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap =
                            new HashMap<String, List<ConfiguratorNodePOJO>>();
                        if (secondLineNodes!=null && secondLineNodes.size()>0) {
                            for (ConfiguratorNodePOJO node : secondLineNodes) {
                                if (node.getPrintGroupLevel() != null &&
                                    node.getPrintGroupLevel().equalsIgnoreCase("1")) {
                                    if (node.getExtendedPrice() != null) {
                                        Double b =
                                            new Double(node.getExtendedPrice());

                                        sumQuoteTotal = sumQuoteTotal + b;
                                        System.out.println("SumTotal " +
                                                           sumQuoteTotal);
                                        //sumQuoteTotal = sumQuoteTotal+Integer.parseInt(node.getExtendedPrice());
                                    }
                                    //quoteTotal.setValue(node.getExtendedPrice());
                                }
                            }
                        }
                        for (ConfiguratorNodePOJO node : allNodesList) {
                            if (node.getPrintGroupLevel() != null &&
                                node.getPrintGroupLevel().equalsIgnoreCase("1")) {
                                if (node.getExtendedPrice() != null) {
                                    Double b =
                                        new Double(node.getExtendedPrice());

                                    sumQuoteTotal = sumQuoteTotal + b;
                                    System.out.println("SumTotal " +
                                                       sumQuoteTotal);
                                    //sumQuoteTotal = sumQuoteTotal+Integer.parseInt(node.getExtendedPrice());
                                }
                                quoteTotal.setValue(sumQuoteTotal);
                            }
                            if (node.getNodeCategory() != null &&
                                node.getPrintGroupLevel() != null) {
                                catList.add(node.getNodeCategory() + "-" +
                                            (node.getPrintGroupLevel() !=
                                             null ? node.getPrintGroupLevel() :
                                             "0"));
                            }
                        }
                        distinctList = removeDuplicatesFromList(catList);
                        for (String distinctCategory : distinctList) {
                            List<ConfiguratorNodePOJO> temp =
                                new ArrayList<ConfiguratorNodePOJO>();
                            for (ConfiguratorNodePOJO node : allNodesList) {
                                if (distinctCategory != null &&
                                    distinctCategory.equalsIgnoreCase(node.getNodeCategory() +
                                                                      "-" +
                                                                      node.getPrintGroupLevel())) {
                                    temp.add(node);
                                }
                            }
                            allNodesByCategoriesMap.put(distinctCategory,
                                                        temp);
                        }
                        root = new ArrayList<NodeCategory>();
                        Iterator it =
                            allNodesByCategoriesMap.entrySet().iterator();
                        NodeCategory firstLevel = null;
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            String Key = (String)pair.getKey();
                            String[] arr = Key.split("-");
                            String category = arr[0];
                            String printGrpLevel = arr[1];
                            firstLevel =
                                    new NodeCategory(category, null, null, null,
                                                     null, null, null, null,
                                                     printGrpLevel);
                            root.add(firstLevel);
                            List<ConfiguratorNodePOJO> childList =
                                (List<ConfiguratorNodePOJO>)pair.getValue();
                            for (ConfiguratorNodePOJO node : childList) {
                                NodeCategory secondLevel =
                                    new NodeCategory(category,
                                                     node.getNodeName(),
                                                     node.getNodeDescription(),
                                                     node.getNodeQty(),
                                                     node.getNodeValue(),
                                                     node.getUnitPrice(),
                                                     node.getExtendedPrice(),
                                                     node.getNodeColor(),
                                                     node.getPrintGroupLevel());
                                firstLevel.addNodes(secondLevel);
                            }

                        }
                        //Trying to sort root
                        for (NodeCategory nc : root) {
                            System.out.println(nc.getCategory() + " " +
                                               nc.getPrintGroupLevel());
                        }
                        NodeComparator comparator = new NodeComparator();
                        Collections.sort(root, comparator);

                        categoryTree =
                                new ChildPropertyTreeModel(root, "childNodes");
                        ADFUtils.setSessionScopeValue("categoryTree",
                                                      categoryTree);

                    } else {
                        ADFUtils.setSessionScopeValue("quoteNumber",
                                                      null); //If exception occurs , Quoting should be loaded in create mode, Not in update mode
                        root = new ArrayList();
                        categoryTree =
                                new ChildPropertyTreeModel(root, "childNodes");
                        ADFUtils.setSessionScopeValue("categoryTree",
                                                      categoryTree);
                        RichPopup.PopupHints hints =
                            new RichPopup.PopupHints();
                        errPopup.show(hints); // Show error here
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ADFUtils.setSessionScopeValue("ImpSrcChanged", null);
            //categroryTreeLineTwo = null ;
        }
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
            File file =
                new File("D://Projects//Advantest//JsonResponse/exportTarget.xml");
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
        } finally {
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

    public String getUserFileName() {
        if (userInputFileName != null) {
            return (String)userInputFileName.getValue();
        } else
            return "ExportedXML";
    }

    //helper methods

    public List<ConfiguratorNodePOJO> getAllNodes(String lineNum) {
        Object parentObj = ADFUtils.getSessionScopeValue("parentObject");
        if (parentObj != null) {
            v93Obj = (V93kQuote)parentObj;
            allNodes = parseAllNodes(v93Obj, lineNum);
        }
        return allNodes;
    }

    private List<ConfiguratorNodePOJO> parseAllNodes(V93kQuote v93Obj,
                                                     String lineNum) {
        ArrayList<ConfiguratorNodePOJO> nodeList = null;
        ArrayList<QuoteLinePOJO> quoteLineListRef =
            v93Obj.getReferenceConfigurationLines();
        ArrayList<QuoteLinePOJO> quoteLineListTarget =
            v93Obj.getTargetConfigurationLines();
        System.out.println("Size of quoteLineref " + quoteLineListRef.size());
        if (lineNum != null && lineNum.equalsIgnoreCase("1")) {
            //for(QuoteLinePOJO quoteLineRef : quoteLineListRef){
            if (quoteLineListTarget != null &&
                quoteLineListTarget.size() >= 0) {
                nodeList = quoteLineListTarget.get(0).getItems(); //First line
            }
            //}
        }
        if (lineNum != null && lineNum.equalsIgnoreCase("2")) {
            // for(QuoteLinePOJO quoteTargetRef : quoteLineListTarget){
            if (quoteLineListTarget != null &&
                quoteLineListTarget.size() > 0) {
                if (quoteLineListTarget != null &&
                    quoteLineListTarget.size() > 1) {
                    nodeList =
                            quoteLineListTarget.get(1).getItems(); //2nd line
                }
            }
            //  }
        }
        // v93Obj.getNodeCollection()
        //        if (targetCollection != null && !targetCollection.isEmpty()) {
        //            if (lineNum != null && lineNum.equalsIgnoreCase("1")) {
        //                //get Line1 node collection
        //                nodeList =
        //                        targetCollection.get("1.0"); // Line 1 node collection
        //            } else if (lineNum != null && lineNum.equalsIgnoreCase("2")) {
        //                nodeList =
        //                        targetCollection.get("20000"); // Line 2 node collection , This key should be 2,0
        //            }
        //        }


        return nodeList;
    }

    private List<String> removeDuplicatesFromList(List<String> inputList) {

        Set<String> set = new HashSet<String>(inputList);
        List<String> outputList = new ArrayList<String>();
        outputList.clear();
        outputList.addAll(set);
        return outputList;
    }

    public ChildPropertyTreeModel getCategroryTreeLineTwo() {
        try {
            StringBuilder errMessage = new StringBuilder("Error");
            String refreshImport =
                (String)ADFUtils.getSessionScopeValue("refreshImport");
            Object parentObj = ADFUtils.getSessionScopeValue("parentObject");
            String impSrcChanged =
                (String)ADFUtils.getSessionScopeValue("ImpSrcChanged");
            if (impSrcChanged != null && impSrcChanged.equalsIgnoreCase("Y")) {
                categroryTreeLineTwo = null;
                //quoteTotal.setValue(null);
            }
            System.out.println("For line 2  refresh imp" + refreshImport +
                               " Cat tree " + categroryTreeLineTwo +
                               " Parent obj " + parentObj);
            if (refreshImport != null && refreshImport.equalsIgnoreCase("Y") &&
                parentObj != null) {
                if (parentObj != null) {
                    V93kQuote obj = (V93kQuote)parentObj;
                    //Check if no exceptions from configurator
                    if (obj.getExceptionMap() != null) {
                        TreeMap<String, ArrayList<String>> exceptionMap =
                            obj.getExceptionMap().getErrorList();
                        List<String> errorMessages =
                            obj.getExceptionMap().getErrorsMessages();

                        if (exceptionMap != null && exceptionMap.size() > 0) {

                            for (Map.Entry<String, ArrayList<String>> entry :
                                 exceptionMap.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<String> value = entry.getValue();
                                for (String str : value) {
                                    errMessage.append(str);
                                }
                            }
                        }
                        if (errorMessages != null &&
                            errorMessages.size() > 0) {
                            for (String str : errorMessages) {
                                errMessage.append(str);
                            }
                        }

                        if (errMessage != null &&
                            !errMessage.toString().equalsIgnoreCase("Error")) {
                            //RichPopup.
                        }


                    }


                    if (errMessage != null &&
                        errMessage.toString().equalsIgnoreCase("Error")) {

                        List<String> catList = new ArrayList<String>();
                        List<String> distinctList = new ArrayList<String>();
                        List<ConfiguratorNodePOJO> allNodesList =
                            getAllNodes("2");
                        // obj.getNodeCollection();
                        HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap =
                            new HashMap<String, List<ConfiguratorNodePOJO>>();

                        if (allNodesList != null && !allNodesList.isEmpty()) {
                            for (ConfiguratorNodePOJO node : allNodesList) {
                                if (node.getNodeCategory() != null &&
                                    node.getPrintGroupLevel() != null) {
                                    catList.add(node.getNodeCategory() + "-" +
                                                (node.getPrintGroupLevel() !=
                                                 null ?
                                                 node.getPrintGroupLevel() :
                                                 "0"));
                                }
                            }
                            distinctList = removeDuplicatesFromList(catList);
                            for (String distinctCategory : distinctList) {
                                List<ConfiguratorNodePOJO> temp =
                                    new ArrayList<ConfiguratorNodePOJO>();
                                for (ConfiguratorNodePOJO node :
                                     allNodesList) {
                                    if (distinctCategory != null &&
                                        distinctCategory.equalsIgnoreCase(node.getNodeCategory() +
                                                                          "-" +
                                                                          node.getPrintGroupLevel())) {
                                        temp.add(node);
                                    }
                                }
                                allNodesByCategoriesMap.put(distinctCategory,
                                                            temp);
                            }
                            root = new ArrayList<NodeCategory>();
                            Iterator it =
                                allNodesByCategoriesMap.entrySet().iterator();
                            NodeCategory firstLevel = null;
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                String Key = (String)pair.getKey();
                                System.out.println("Key " + Key);
                                String[] arr = Key.split("-");
                                String category = arr[0];
                                String printGrpLevel = arr[1];
                                firstLevel =
                                        new NodeCategory(category, null, null,
                                                         null, null, null,
                                                         null, null,
                                                         printGrpLevel);
                                root.add(firstLevel);
                                List<ConfiguratorNodePOJO> childList =
                                    (List<ConfiguratorNodePOJO>)pair.getValue();
                                for (ConfiguratorNodePOJO node : childList) {
                                    NodeCategory secondLevel =
                                        new NodeCategory(category,
                                                         node.getNodeName(),
                                                         node.getNodeDescription(),
                                                         node.getNodeQty(),
                                                         node.getNodeValue(),
                                                         node.getUnitPrice(),
                                                         node.getExtendedPrice(),
                                                         node.getNodeColor(),
                                                         node.getPrintGroupLevel());
                                    firstLevel.addNodes(secondLevel);
                                }

                            }
                            //Trying to sort root

                            NodeComparator comparator = new NodeComparator();
                            Collections.sort(root, comparator);

                            categroryTreeLineTwo =
                                    new ChildPropertyTreeModel(root,
                                                               "childNodes");
                        }

                    } else {
                        root = new ArrayList();
                        categroryTreeLineTwo =
                                new ChildPropertyTreeModel(root, "childNodes");
                        RichPopup.PopupHints hints =
                            new RichPopup.PopupHints();
                        //errorPopup.show(hints); // Show error popup
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //cleanup
            ADFUtils.setSessionScopeValue("ImpSrcChanged", null);
        }
        return categroryTreeLineTwo;
    }

    public void setErrPopup(RichPopup errPopup) {
        this.errPopup = errPopup;
    }

    public RichPopup getErrPopup() {
        return errPopup;
    }

    public void setErrorText(RichOutputFormatted errorText) {
        this.errorText = errorText;
    }

    public RichOutputFormatted getErrorText() {
        return errorText;
    }

    public void setQuoteTotal(RichOutputText quoteTotal) {
        this.quoteTotal = quoteTotal;
    }

    public RichOutputText getQuoteTotal() {
        return quoteTotal;
    }
}
