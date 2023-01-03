package com.example.iotassistantrest.utils;

import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.firebase.body.data.Switch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JSONUtils {
    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    public static String objectToJson(Object obj){
        ObjectMapper jsonMapper = new JsonMapper();
        String json = "";
        try {
            json = jsonMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return json;
    }

    public static Config jsonToConfig(String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, Config.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String,Switch> jsonToSwitches(String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, new TypeReference<Map<String,Switch>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

/*    public static List<Switch> jsonToListSwitches(String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, Config.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*public static <T> Class<T> jsonToObject(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return (Class<T>) mapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
