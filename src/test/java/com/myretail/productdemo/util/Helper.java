package com.myretail.productdemo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class Helper {

    public static <T> T getObjectFromFile(String filePath, Class<T> valueType) {
        T result = null;
        try {
            ClassLoader classLoader = valueType.getClassLoader();
            final String json = IOUtils.toString(classLoader.getResourceAsStream(filePath));
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            result = objectMapper.readValue(json, valueType);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String loadJson(String filePath, ClassLoader classLoader) {
        try {
            final String json = IOUtils.toString(classLoader.getResourceAsStream(filePath), Charset.defaultCharset());
            return json;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void assertEquals(Object expected, Object actual) {
        boolean areEqual = true;

        if (expected == null) {
            areEqual = actual == null;
        } else {
            areEqual = expected.equals(actual);
        }

        if (!areEqual) {
            throw new AssertionError(expected);
        }

    }

    public static void assertObjectsEqual(Object expected, Object actual) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> expectedMap = objectMapper.convertValue(expected, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> actualMap = objectMapper.convertValue(actual, new TypeReference<Map<String, Object>>() {
        });

        assertEquals(expectedMap, actualMap);
    }
    public static boolean objectsEquals(Object expected, Object actual) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> expectedMap = objectMapper.convertValue(expected, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> actualMap = objectMapper.convertValue(actual, new TypeReference<Map<String, Object>>() {
        });

        return expectedMap.equals(actualMap);
    }

}
