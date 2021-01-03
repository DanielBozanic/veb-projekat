package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import beans.ImeTipa;

public class CustomImeTipaEnumSerializer extends JsonSerializer<ImeTipa> {

	@Override
    public void serialize(final ImeTipa imeTipa, final JsonGenerator gen, final SerializerProvider serializer) 
    		throws IOException, JsonProcessingException
    {
    	gen.writeString(imeTipa.toString());
    }
}
