package com.webservice.bookstore.json;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class JsonData {

    private JSONArray jsonArray;

    public JsonData(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JsonData(String filename) throws Exception{

        FileInputStream input=new FileInputStream(filename);
        InputStreamReader reader=new InputStreamReader(input,"UTF-8");

        JSONParser parser = new JSONParser();
        Object obj =parser.parse(reader);
        jsonArray = (JSONArray)obj;
    }
    public JSONArray getJsonArray(){
        return jsonArray;
    }
}
