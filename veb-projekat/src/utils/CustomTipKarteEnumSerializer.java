package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import beans.TipKarte;


public class CustomTipKarteEnumSerializer extends JsonSerializer<TipKarte> {

	@Override
    public void serialize(final TipKarte tipKarte, final JsonGenerator gen, final SerializerProvider serializer) 
    		throws IOException, JsonProcessingException
    {
    	gen.writeString(tipKarte.toString());
    }
}
