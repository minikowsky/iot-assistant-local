package com.example.iotassistantrest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
