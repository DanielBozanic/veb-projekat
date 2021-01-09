package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import beans.StatusKarte;

public class CustomStatusKarteEnumSerializer extends JsonSerializer<StatusKarte> {

	@Override
    public void serialize(final StatusKarte statusKarte, final JsonGenerator gen, final SerializerProvider serializer) 
    		throws IOException, JsonProcessingException
    {
    	gen.writeString(statusKarte.toString());
    }
}
