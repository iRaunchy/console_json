package com.techtask.console_json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@Service
public class CommandLineJSONProcessor {

    private static int objectCount = 0;
    private static int valueCount = 0;

    @Value("${filepath:unknown}")
    private String filePath;

    @Value("${objectName:unknown}")
    private String objectName;

    @Value("${objectValue:unknown}")
    private String objectValue;

    public String processJSONWithParameters(String filePath, String name, String value) {

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(filePath));
            searchJSON((JSONObject) obj, name, value);

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

    private static void searchJSON(JSONObject jsonObj, String name, String value) {
        for (Object keyObj : jsonObj.keySet()) {
            String key = (String) keyObj;
            Object valObj = jsonObj.get(key);
            if (valObj instanceof JSONObject) {
                searchJSON((JSONObject) valObj, name, value);
            } else {
                JSONArray person = (JSONArray) valObj;
                Iterator i = person.iterator();
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    for (Object inKeyObj : innerObj.keySet()) {
                        String inKey = (String) inKeyObj;
                        Object inValObj = innerObj.get(inKey);
                        if (inValObj instanceof JSONObject) {
                            searchJSON((JSONObject) inValObj, name, value);
                        }
                        if (innerObj.containsKey(name)) {
                            objectCount++;
                        }
                        if (innerObj.get(name).equals(value)) {
                            valueCount++;
                        }
                    }
                }
            }
        }
    }

        }
