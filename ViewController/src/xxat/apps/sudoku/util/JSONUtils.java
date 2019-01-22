package xxat.apps.sudoku.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import xxat.apps.sudoku.viewmodel.pojo.V93kQuote;

public class JSONUtils {
    public JSONUtils() {
        super();
    }

    public static Object convertJsonToObject(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(new File("D:\\FileStore\\V93kQuote.json"), V93kQuote.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj; 
    }
    
    public static String convertObjToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null ;
        try {
            mapper.writeValue(new File("D:\\FileStore\\V93kQuote.json"), obj);
            jsonInString = mapper.writeValueAsString(obj);
            
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
