package xxatcust.oracle.apps.sudoku.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import xxatcust.oracle.apps.sudoku.viewmodel.pojo.V93kQuote;

public class JSONUtils {
    public JSONUtils() {
        super();
    }

    public static Object convertJsonToObject(String jsonString) throws IOException,
                                                                       JsonParseException,
                                                                       JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
            obj = mapper.readValue(new File("D://Projects//Advantest//JsonResponse/response.json"), V93kQuote.class);
        return obj; 
    }
    
    public static String convertObjToJson(Object obj) throws IOException,
                                                             JsonGenerationException,
                                                             JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null ;
            mapper.writeValue(new File("D:\\FileStore\\V93kQuote.json"), obj);
            jsonInString = mapper.writeValueAsString(obj);
        return jsonInString;
    }
    
    public static void prettyPrintJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
}
