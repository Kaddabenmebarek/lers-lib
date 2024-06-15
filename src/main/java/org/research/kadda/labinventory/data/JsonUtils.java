package org.research.kadda.labinventory.data;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Project: wslib
 *
 * Author: Kadda
 */

public class JsonUtils {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final Charset ISO = Charset.forName("ISO-8859-1");
	
    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
   
	public static <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static JsonNode getJsonNode(String jsonData, String nodeName) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readTree(jsonData).get(nodeName);
    }
    
    private static ObjectMapper getObjectMapper() {
    	ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
	}
    
	public static String formatUTF8(String json) {
		String jsonFormated = new String(json.getBytes(ISO),UTF_8);
		return jsonFormated;
	}
}
