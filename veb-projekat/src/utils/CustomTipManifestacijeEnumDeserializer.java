package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import beans.TipManifestacije;

public class CustomTipManifestacijeEnumDeserializer extends JsonDeserializer<TipManifestacije> {
	
	@Override
    public TipManifestacije deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
		String jsonValue = jsonParser.getText();

        return TipManifestacije.fromString(jsonValue);
    }
}
