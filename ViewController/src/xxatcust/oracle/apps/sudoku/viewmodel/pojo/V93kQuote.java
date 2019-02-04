package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "V93000-Quote")
public class V93kQuote {
    QHeader QheaderObject;
    Config ConfigObject;
    List<ConfiguratorNodePOJO> nodeCollection ;
    SessionDetails sessionDetails ;
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
}
