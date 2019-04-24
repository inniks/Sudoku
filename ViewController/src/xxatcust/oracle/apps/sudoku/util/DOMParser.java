package xxatcust.oracle.apps.sudoku.util;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;

import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.Config;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.ConfiguratorNodePOJO;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.ModelBom;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.PogoMappingFile;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.QHeader;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.QuoteLinePOJO;
import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class DOMParser {
    public DOMParser() {
        super();
    }

    public static File XMLWriterDOM(V93kQuote v93kObj) {
        DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            DOMSource source = new DOMSource(doc);
            //This is the Root element v93000-Quote
            Element rootElement = doc.createElementNS(null, "V93000-Quote");
            //Attach the root to document
            doc.appendChild(rootElement);


            if (v93kObj.getQheaderObject() != null) {
                //First create qheader if it exists
                rootElement.appendChild(qHeaderNode(v93kObj.getQheaderObject(),
                                                    doc));
            }


            //Now create ModelBom objects based on the print sequence logic ,
            List<String> catList = new ArrayList<String>();
            List<String> distinctList = new ArrayList<String>();
            List<ConfiguratorNodePOJO> allNodesList =
                getAllNodes(v93kObj, "2");
            HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap =
                new HashMap<String, List<ConfiguratorNodePOJO>>();

            for (ConfiguratorNodePOJO node : allNodesList) {
                //                            if (node.getPrintGroupLevel() != null &&
                //                                node.getPrintGroupLevel().equalsIgnoreCase("1")) {
                //                                quoteTotal.setValue(node.getExtendedPrice());
                //                            }
//                if (node.getNodeCategory() != null &&
//                    node.getPrintGroupLevel() != null) {
//                    catList.add(node.getNodeCategory() + "-" +
//                                (node.getPrintGroupLevel() != null ?
//                                 node.getPrintGroupLevel() : "0"));
//                }
                if(node.getXmlTag()!=null){
                    catList.add(node.getXmlTag()) ;
                }
            }
            distinctList = removeDuplicatesFromList(catList);

            for (String distinctCategory : distinctList) {
                List<ConfiguratorNodePOJO> temp =
                    new ArrayList<ConfiguratorNodePOJO>();
                for (ConfiguratorNodePOJO node : allNodesList) {
//                    if (distinctCategory != null &&
//                        distinctCategory.equalsIgnoreCase(node.getNodeCategory() +
//                                                          "-" +
//                                                          node.getPrintGroupLevel())) {
//                        temp.add(node);
//                    }
                    if(distinctCategory!=null && distinctCategory.equalsIgnoreCase(node.getXmlTag())){
                        temp.add(node);
                    }
                }
                allNodesByCategoriesMap.put(distinctCategory, temp);
            }
            // Now we have the allNodes by category map with us ,Iterate through each category and create nodes based on the node category
            if (v93kObj.getConfigObject() != null &&
                v93kObj.getConfigObject().getModelbomObject() != null) {
                //rootElement.appendChild(modelBomNode(v93kObj.getConfigObject().getModelbomObject(), doc)) ;
            }

            rootElement.appendChild(configPmfNode(v93kObj, doc,
                                                  allNodesByCategoriesMap));


            StreamResult console = new StreamResult(System.out);
            //            StreamResult file =
            //                new StreamResult(new File("D://Projects//Advantest//JsonResponse/DOMExport.xml"));
            transformer.transform(source, console);
            //transformer.transform(source, file);
        } catch (Exception pce) {
            // TODO: Add catch code
            pce.printStackTrace();
        }
        return null;
    }

    private static Node getNode(Document doc, String id, String name,
                                String age, String role, String gender) {
        Element employee = doc.createElement("Employee");

        //set id attribute
        employee.setAttribute("id", id);

        //create name element
        employee.appendChild(getNodeElements(doc, employee, "name", name));

        //create age element
        employee.appendChild(getNodeElements(doc, employee, "age", age));

        //create role element
        employee.appendChild(getNodeElements(doc, employee, "role", role));

        //create gender element
        employee.appendChild(getNodeElements(doc, employee, "gender", gender));

        return employee;
    }

    private static Node getNodeElements(Document doc, Element element,
                                        String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private static ArrayList<String> removeDuplicatesFromList(List<String> inputList) {

        Set<String> set = new HashSet<String>(inputList);
        List<String> outputList = new ArrayList<String>();
        outputList.clear();
        outputList.addAll(set);
        return (ArrayList<String>)outputList;
    }

    private static Node qHeaderNode(QHeader qheaderObj, Document doc) {
        //parse the qheader object and create nodes accordingly
        Element qheadernode = doc.createElement("qheader");
        if (qheaderObj.getQtitle() != null) {
            Node qTitleNode =
                getNodeElements(doc, null, "qtitle", qheaderObj.getQtitle());
            qheadernode.appendChild(qTitleNode);
        }

        //create customer node
        if (qheaderObj.getCustomerObject() != null) {
            Element customerNode = doc.createElement("customer");
            if (qheaderObj.getCustomerObject().getCnumber() != null) {
                Node cnumNode =
                    getNodeElements(doc, null, "cnumber", qheaderObj.getCustomerObject().getCnumber());
                customerNode.appendChild(cnumNode);
            }
            if (qheaderObj.getCustomerObject().getCaddr1() != null) {
                Node caddr1 =
                    getNodeElements(doc, null, "caddr1", qheaderObj.getCustomerObject().getCaddr1());
                customerNode.appendChild(caddr1);
            }
            if (qheaderObj.getCustomerObject().getCaddr2() != null) {
                Node caddr2 =
                    getNodeElements(doc, null, "caddr2", qheaderObj.getCustomerObject().getCaddr2());
                customerNode.appendChild(caddr2);
            }
            if (qheaderObj.getCustomerObject().getCphone() != null) {
                Node cphone =
                    getNodeElements(doc, null, "cphone", qheaderObj.getCustomerObject().getCphone());
                customerNode.appendChild(cphone);
            }
            if (qheaderObj.getCustomerObject().getCname() != null) {
                Node cname =
                    getNodeElements(doc, null, "cname", qheaderObj.getCustomerObject().getCname());
                customerNode.appendChild(cname);
            }
            if (qheaderObj.getCustomerObject().getCcontact() != null) {
                Node ccontact =
                    getNodeElements(doc, null, "ccontact", qheaderObj.getCustomerObject().getCcontact());
                customerNode.appendChild(ccontact);
            }
            if (qheaderObj.getCustomerObject().getCemail() != null) {
                Node cemail =
                    getNodeElements(doc, null, "cemail", qheaderObj.getCustomerObject().getCemail());
                customerNode.appendChild(cemail);
            }
            if (qheaderObj.getCustomerObject().getGlobalcmpy() != null) {
                Node globalcmpy =
                    getNodeElements(doc, null, "globalcmpy", qheaderObj.getCustomerObject().getGlobalcmpy());
                customerNode.appendChild(globalcmpy);
            }

            //Add customer node to quoteheadernode once all attributes are taken care of
            qheadernode.appendChild(customerNode);
        }
        //Customer done,Now start contract object
        if (qheaderObj.getContractObject() != null) {
            Element contractNode = doc.createElement("conract");
            if (qheaderObj.getContractObject().getConfixedpr() != null) {
                Node confixedpr =
                    getNodeElements(doc, null, "confixedpr", qheaderObj.getContractObject().getConfixedpr());
                contractNode.appendChild(confixedpr);
            }
            if (qheaderObj.getContractObject().getConwty() != null) {
                Node conwty =
                    getNodeElements(doc, null, "conwty", qheaderObj.getContractObject().getConwty());
                contractNode.appendChild(conwty);
            }
            if (qheaderObj.getContractObject().getConinco() != null) {
                Node coninco =
                    getNodeElements(doc, null, "coninco", qheaderObj.getContractObject().getConinco());
                contractNode.appendChild(coninco);
            }
            if (qheaderObj.getContractObject().getConpay() != null) {
                Node conpay =
                    getNodeElements(doc, null, "conpay", qheaderObj.getContractObject().getConpay());
                contractNode.appendChild(conpay);
            }
            if (qheaderObj.getContractObject().getConid() != null) {
                Node conid =
                    getNodeElements(doc, null, "conid", qheaderObj.getContractObject().getConid());
                contractNode.appendChild(conid);
            }
            qheadernode.appendChild(contractNode);
        }

        //Contract Node done , Now created deal node

        if (qheaderObj.getDealObject() != null) {
            Element dealNode = doc.createElement("deal");
            if (qheaderObj.getDealObject().getDealid() != null) {
                Node dealid =
                    getNodeElements(doc, null, "dealid", qheaderObj.getDealObject().getDealid());
                dealNode.appendChild(dealid);
            }
            if (qheaderObj.getDealObject().getSalesch() != null) {
                Node salesch =
                    getNodeElements(doc, null, "salesch", qheaderObj.getDealObject().getSalesch());
                dealNode.appendChild(salesch);
            }
            if (qheaderObj.getDealObject().getStatus() != null) {
                Node status =
                    getNodeElements(doc, null, "status", qheaderObj.getDealObject().getStatus());
                dealNode.appendChild(status);
            }
            if (qheaderObj.getDealObject().getQuoteid() != null) {
                Node quoteid =
                    getNodeElements(doc, null, "quoteid", qheaderObj.getDealObject().getQuoteid());
                dealNode.appendChild(quoteid);
            }
            if (qheaderObj.getDealObject().getSoNumber() != null) {
                Node sonumber =
                    getNodeElements(doc, null, "so-number", qheaderObj.getDealObject().getSoNumber());
                dealNode.appendChild(sonumber);
            }
            if (qheaderObj.getDealObject().getSysid() != null) {
                Node sysid =
                    getNodeElements(doc, null, "sysid", qheaderObj.getDealObject().getSysid());
                dealNode.appendChild(sysid);
            }
            if (qheaderObj.getDealObject().getCurrency() != null) {
                Node currency =
                    getNodeElements(doc, null, "currency", qheaderObj.getDealObject().getCurrency());
                dealNode.appendChild(currency);
            }
            if (qheaderObj.getDealObject().getDdiscount() != null) {
                Node ddiscount =
                    getNodeElements(doc, null, "ddiscount", qheaderObj.getDealObject().getDdiscount());
                dealNode.appendChild(ddiscount);
            }
            if (qheaderObj.getDealObject().getDincoterm() != null) {
                Node dincoterm =
                    getNodeElements(doc, null, "dincoterm", qheaderObj.getDealObject().getDincoterm());
                dealNode.appendChild(dincoterm);
            }
            if (qheaderObj.getDealObject().getDpayterm() != null) {
                Node dpayterm =
                    getNodeElements(doc, null, "dpayterm", qheaderObj.getDealObject().getDpayterm());
                dealNode.appendChild(dpayterm);
            }
            if (qheaderObj.getDealObject().getDwtyterm() != null) {
                Node dwtyterm =
                    getNodeElements(doc, null, "dwtyterm", qheaderObj.getDealObject().getDwtyterm());
                dealNode.appendChild(dwtyterm);
            }
            if (qheaderObj.getDealObject().getQdate() != null) {
                Node qdate =
                    getNodeElements(doc, null, "qdate", qheaderObj.getDealObject().getQdate());
                dealNode.appendChild(qdate);
            }
            if (qheaderObj.getDealObject().getExpdate() != null) {
                Node expdate =
                    getNodeElements(doc, null, "expdate", qheaderObj.getDealObject().getExpdate());
                dealNode.appendChild(expdate);
            }
            if (qheaderObj.getDealObject().getOdate() != null) {
                Node odate =
                    getNodeElements(doc, null, "odate", qheaderObj.getDealObject().getOdate());
                dealNode.appendChild(odate);
            }
            if (qheaderObj.getDealObject().getCrd() != null) {
                Node crd =
                    getNodeElements(doc, null, "crd", qheaderObj.getDealObject().getCrd());
                dealNode.appendChild(crd);

            }
            qheadernode.appendChild(dealNode);
        }

        //Deal node done , Now create Salesteam node
        if (qheaderObj.getSalesteamObject() != null) {
            Element salesteamNode = doc.createElement("salesteam");
            if (qheaderObj.getSalesteamObject().getOu() != null) {
                Node ou =
                    getNodeElements(doc, null, "ou", qheaderObj.getSalesteamObject().getOu());
                salesteamNode.appendChild(ou);
            }
            if (qheaderObj.getSalesteamObject().getSrespo() != null) {
                Node srespo =
                    getNodeElements(doc, null, "srespo", qheaderObj.getSalesteamObject().getSrespo());
                salesteamNode.appendChild(srespo);
            }
            if (qheaderObj.getSalesteamObject().getSphone() != null) {
                Node sphone =
                    getNodeElements(doc, null, "sphone", qheaderObj.getSalesteamObject().getSphone());
                salesteamNode.appendChild(sphone);
            }
            if (qheaderObj.getSalesteamObject().getSemail() != null) {
                Node semail =
                    getNodeElements(doc, null, "semail", qheaderObj.getSalesteamObject().getSemail());
                salesteamNode.appendChild(semail);
            }
            if (qheaderObj.getSalesteamObject().getCsrrespo() != null) {
                Node csrrespo =
                    getNodeElements(doc, null, "csrrespo", qheaderObj.getSalesteamObject().getCsrrespo());
                salesteamNode.appendChild(csrrespo);
            }
            if (qheaderObj.getSalesteamObject().getCsrphone() != null) {
                Node csrphone =
                    getNodeElements(doc, null, "csrphone", qheaderObj.getSalesteamObject().getCsrphone());
                salesteamNode.appendChild(csrphone);
            }
            if (qheaderObj.getSalesteamObject().getCsremail() != null) {
                Node csremail =
                    getNodeElements(doc, null, "csremail", qheaderObj.getSalesteamObject().getCsremail());
                salesteamNode.appendChild(csremail);
            }

            qheadernode.appendChild(salesteamNode);
        }


        return qheadernode;
    }

    private static Node configPmfNode(V93kQuote v93, Document doc,
                                      HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap) {
        Element configNode = doc.createElement("config");
        Config configObj = v93.getConfigObject();
        configNode.appendChild(modelBomNode(v93, doc,
                                            allNodesByCategoriesMap));
        if (configObj != null && configObj.getVersion() != null) {
            configNode.setAttribute("version", configObj.getVersion());
        }
        if (configObj != null && configObj.getPmfObject() != null) {
            Node pmfNode = doc.createElement("pmf");
            List<PogoMappingFile> listPmf =
                configObj.getPmfObject().getPmfMap();
            for (PogoMappingFile pmfObj : listPmf) {
                Element mapNode = doc.createElement("map");
                if (pmfObj.getRefId() != null)
                    mapNode.setAttribute("ref-id", pmfObj.getRefId());
                if (pmfObj.getProductName() != null)
                    mapNode.setAttribute("card", pmfObj.getProductName());
                if (pmfObj.getPogoName() != null)
                    mapNode.setAttribute("cable", pmfObj.getPogoName());
                if (pmfObj.getPogoMapping() != null)
                    mapNode.setTextContent(pmfObj.getPogoMapping());

                pmfNode.appendChild(mapNode);
            }
            configNode.appendChild(pmfNode);
        }


        return configNode;
    }

    private static Node modelBomNode(V93kQuote v93, Document doc,
                                     HashMap<String, List<ConfiguratorNodePOJO>> allNodesByCategoriesMap) {
        Element modelbomNode = doc.createElement("modelbom");
        if (v93.getConfigObject() != null) {
            ModelBom modelBomObj = v93.getConfigObject().getModelbomObject();
            if (modelBomObj.getId() != null) {
                modelbomNode.setAttribute("id", modelBomObj.getId());
            }
            if (modelBomObj.getCid() != null)
                modelbomNode.appendChild(getNodeElements(doc, null, "cid",
                                                         modelBomObj.getCid()));
            if (modelBomObj.getPricelist() != null)
                modelbomNode.appendChild(getNodeElements(doc, null,
                                                         "pricelist",
                                                         modelBomObj.getPricelist()));
            if (modelBomObj.getClassObject() != null) {
                Element classNode = doc.createElement("class");
                if (modelBomObj.getClassObject().getId() != null)
                    classNode.setAttribute("id",
                                           modelBomObj.getClassObject().getId());
                if (modelBomObj.getClassObject().getCompatibility() != null)
                    classNode.setAttribute("compatibility",
                                           modelBomObj.getClassObject().getCompatibility());
                modelbomNode.appendChild(classNode);
            }
        }
        //Iterate over the allCat map and write to xml

        for (Map.Entry<String, List<ConfiguratorNodePOJO>> entry :
             allNodesByCategoriesMap.entrySet()) {
            String key = entry.getKey();
            System.out.println("Key is " + key);
            List<ConfiguratorNodePOJO> listItems = entry.getValue();

            Element itemNode = doc.createElement(key);

            for (ConfiguratorNodePOJO node : listItems) {
                System.out.println("Item is " + node.getNodeName());
                Element e = doc.createElement("item");
                if (node.getNodeName() != null) {

                    e.setTextContent(node.getNodeName());
                }
                if (node.getNodeQty() != null) {
                    e.setAttribute("qty", node.getNodeQty());
                }
                itemNode.appendChild(e);
            }
            modelbomNode.appendChild(itemNode);
        }

        return modelbomNode;
    }

    private static List<ConfiguratorNodePOJO> getAllNodes(V93kQuote parentObj,
                                                          String lineNum) {
        List allNodes = null;
        if (parentObj != null) {
            allNodes = parseAllNodes(parentObj, lineNum);
        }
        return allNodes;
    }

    private static List<ConfiguratorNodePOJO> parseAllNodes(V93kQuote v93Obj,
                                                            String lineNum) {
        ArrayList<ConfiguratorNodePOJO> nodeList = null;
        ArrayList<QuoteLinePOJO> quoteLineListRef =
            v93Obj.getReferenceConfigurationLines();
        ArrayList<QuoteLinePOJO> quoteLineListTarget =
            v93Obj.getTargetConfigurationLines();
        System.out.println("Size of quoteLineref " + quoteLineListRef.size());
        if (lineNum != null && lineNum.equalsIgnoreCase("1")) {
            //for(QuoteLinePOJO quoteLineRef : quoteLineListRef){
            nodeList = quoteLineListTarget.get(0).getItems(); //First line
            //}
        }
        if (lineNum != null && lineNum.equalsIgnoreCase("2")) {
            // for(QuoteLinePOJO quoteTargetRef : quoteLineListTarget){
            nodeList = quoteLineListTarget.get(1).getItems(); //2nd line
            //  }
        }
        return nodeList;

    }


}
