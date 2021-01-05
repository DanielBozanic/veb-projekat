package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class PomocneFunkcije {
	
	public static void upisi(Object lista, String putanja) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(putanja), lista);
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T ucitaj(File fajl, TypeReference<T> typeRef) {
    	ObjectMapper mapper = new ObjectMapper();
        T t = (T) new ArrayList<T>();
        if (!fajl.exists())
        	return t;
        try {
            t = mapper.readValue(fajl, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
	
	public static void kreirajBaseFolder() {
		File folder = new File(Konstante.BASE_FOLDER);
	    if (!folder.exists()){
	    	folder.mkdir();
	    }
	}
	
}
