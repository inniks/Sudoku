package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "V93000-Quote")
public class V93kQuote {
    QHeader QheaderObject;
    Config ConfigObject;
    List<ConfiguratorNodePOJO> nodeCollection ;
    ArrayList<QuoteLinePOJO> referenceConfigurationLines;
    ArrayList<QuoteLinePOJO> targetConfigurationLines;
    TreeMap<String, ArrayList<ConfiguratorNodePOJO>> referenceNodeCollection ;
        TreeMap<String, ArrayList<ConfiguratorNodePOJO>> targetNodeCollection ;
    SessionDetails sessionDetails ;
    ExceptionHandlerPOJO exceptionMap ;
    InputParams inputParams ;
    public V93kQuote() {
        super();
    }

    public void setQheaderObject(QHeader QheaderObject) {
        this.QheaderObject = QheaderObject;
    }
    @XmlElement(name = "qheader")
    public QHeader getQheaderObject() {
        return QheaderObject;
    }

    public void setConfigObject(Config ConfigObject) {
        this.ConfigObject = ConfigObject;
    }
    @XmlElement(name = "config")
    public Config getConfigObject() {
        return ConfigObject;
    }

    public void setNodeCollection(List<ConfiguratorNodePOJO> nodeCollection) {
        this.nodeCollection = nodeCollection;
    }

    public List<ConfiguratorNodePOJO> getNodeCollection() {
        return nodeCollection;
    }

    public void setSessionDetails(SessionDetails sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    public SessionDetails getSessionDetails() {
        return sessionDetails;
    }

    public void setExceptionMap(ExceptionHandlerPOJO exceptionMap) {
        this.exceptionMap = exceptionMap;
    }

    public ExceptionHandlerPOJO getExceptionMap() {
        return exceptionMap;
    }

    public void setInputParams(InputParams inputParams) {
        this.inputParams = inputParams;
    }

    public InputParams getInputParams() {
        return inputParams;
    }

    public void setReferenceConfigurationLines(ArrayList<QuoteLinePOJO> referenceConfigurationLines) {
        this.referenceConfigurationLines = referenceConfigurationLines;
    }

    public ArrayList<QuoteLinePOJO> getReferenceConfigurationLines() {
        return referenceConfigurationLines;
    }

    public void setTargetConfigurationLines(ArrayList<QuoteLinePOJO> targetConfigurationLines) {
        this.targetConfigurationLines = targetConfigurationLines;
    }

    public ArrayList<QuoteLinePOJO> getTargetConfigurationLines() {
        return targetConfigurationLines;
    }

    public void setReferenceNodeCollection(TreeMap<String, ArrayList<ConfiguratorNodePOJO>> referenceNodeCollection) {
        this.referenceNodeCollection = referenceNodeCollection;
    }

    public TreeMap<String, ArrayList<ConfiguratorNodePOJO>> getReferenceNodeCollection() {
        return referenceNodeCollection;
    }

    public void setTargetNodeCollection(TreeMap<String, ArrayList<ConfiguratorNodePOJO>> targetNodeCollection) {
        this.targetNodeCollection = targetNodeCollection;
    }

    public TreeMap<String, ArrayList<ConfiguratorNodePOJO>> getTargetNodeCollection() {
        return targetNodeCollection;
    }
}
