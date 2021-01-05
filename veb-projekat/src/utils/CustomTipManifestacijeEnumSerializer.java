package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import beans.TipManifestacije;

public class CustomTipManifestacijeEnumSerializer extends JsonSerializer<TipManifestacije> {
	
	@Override
    public void serialize(final TipManifestacije tipManifestacije, final JsonGenerator gen, final SerializerProvider serializer) 
    		throws IOException, JsonProcessingException
    {
    	gen.writeString(tipManifestacije.toString());
    }
}
