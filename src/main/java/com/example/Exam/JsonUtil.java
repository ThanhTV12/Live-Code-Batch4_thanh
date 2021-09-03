package com.example.Exam;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public static <T> T convert(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException ex) {
            return null;
        }
    }

    public static String convertToJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException ex) {
            return null;
        }
    }

    private static ObjectMapper mapper = new ObjectMapper();
}
