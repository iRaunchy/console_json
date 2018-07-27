package com.techtask.console_json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@Service
public class CommandLineJSONProcessor {

    private static int objectCount = 0;
    private static int valueCount = 0;

    public String processJSONWithParameters(String filePath, String name, String value) {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(filePath));
            scanObject((JSONObject) obj, name, value);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String result = "*** Found " + valueCount + " objects with field " + name + " equals " + value + " **** \n" +
                "*** Found " + objectCount + " " + name + " ***";
        return result;
    }

    private static void scanObject(JSONObject node, String name, String value) {

        for (Object key : node.keySet()) {

            String field = (String) key;
            Object fieldValue = node.get(field);

            if (fieldValue instanceof JSONObject) {
                scanObject((JSONObject) fieldValue, name, value);
            } else if (fieldValue.toString().startsWith("[")) {
                scanArray((JSONArray) fieldValue, name, value);
            }

            if (field.equals(name)) {
                objectCount++;
                if (fieldValue.equals(value)) {
                    valueCount++;
                }
            }
        }
    }

    private static void scanArray(JSONArray array, String name, String value) {
        Iterator i = array.iterator();
        while (i.hasNext()) {
            JSONObject innerObj = (JSONObject) i.next();
            scanObject(innerObj, name, value);
        }

    }

}
