package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import beans.ImeTipa;


public class CustomImeTipaEnumDeserializer extends JsonDeserializer<ImeTipa> {

	@Override
    public ImeTipa deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
		String jsonValue = jsonParser.getText();

        return ImeTipa.fromString(jsonValue);
    }
}
