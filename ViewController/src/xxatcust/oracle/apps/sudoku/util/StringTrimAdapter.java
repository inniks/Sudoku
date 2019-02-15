package xxatcust.oracle.apps.sudoku.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

public class StringTrimAdapter {
    public StringTrimAdapter() {
        super();
    }

    public static String removeLeadingSpaces(String param) {
        if (param == null) {
            return null;
        }

        if (param.isEmpty()) {
            return "";
        }

        int arrayIndex = 0;
        while (true) {
            if (!Character.isWhitespace(param.charAt(arrayIndex++))) {
                break;
            }
        }
        return param.substring(arrayIndex - 1);
    }
    
    public static String stripSpaces(String input){
        String stripped = StringUtils.stripEnd(input," ");
        return stripped ;
    }
}
