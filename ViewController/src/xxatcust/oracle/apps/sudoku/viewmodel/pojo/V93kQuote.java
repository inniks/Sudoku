package xxatcust.oracle.apps.sudoku.viewmodel.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "V93000-Quote")
public class V93kQuote {
    QHeader QheaderObject;
    Config ConfigObject;

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
}
