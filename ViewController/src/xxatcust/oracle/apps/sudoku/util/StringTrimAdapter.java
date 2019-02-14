package xxatcust.oracle.apps.sudoku.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringTrimAdapter extends XmlAdapter<String, String> {
    public StringTrimAdapter() {
        super();
    }

    public String unmarshal(String v) throws Exception {
        if (v == null)
            return null;
        System.out.println("Item "+v);
        return v.trim();
    }

    public String marshal(String v) throws Exception {
        if (v == null)
            return null;
        return v.trim();
    }
}
