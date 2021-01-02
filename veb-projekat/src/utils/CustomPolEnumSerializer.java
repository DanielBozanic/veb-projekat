package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import beans.Pol;

public final class CustomPolEnumSerializer extends JsonSerializer<Pol> {
    @Override
    public void serialize(final Pol pol, final JsonGenerator gen, final SerializerProvider serializer) 
    		throws IOException, JsonProcessingException
    {
    	gen.writeString(pol.toString());
    }

}
