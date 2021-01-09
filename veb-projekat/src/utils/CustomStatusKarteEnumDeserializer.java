package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import beans.StatusKarte;


public class CustomStatusKarteEnumDeserializer extends JsonDeserializer<StatusKarte> {
	
	@Override
    public StatusKarte deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
		String jsonValue = jsonParser.getText();

        return StatusKarte.fromString(jsonValue);
    }

}
