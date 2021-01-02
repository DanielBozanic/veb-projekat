package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import beans.Uloga;

public class CustomUlogaEnumDeserializer extends JsonDeserializer<Uloga> {
	
	@Override
    public Uloga deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
		String jsonValue = jsonParser.getText();

        return Uloga.fromString(jsonValue);
    }
}
