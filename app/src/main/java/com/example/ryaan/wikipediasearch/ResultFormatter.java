package com.example.ryaan.wikipediasearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryaan on 16/04/2017.
 */

public class ResultFormatter {
    String originalJsonData;
    JSONArray totalRes,titleJson,contentJson,linksJson;
    List<String> titles ;
    List<String> contents;
    List<String> links;
    public ResultFormatter(String originalJsonData) throws JSONException {
        titles=new ArrayList<String>();
        contents= new ArrayList<String>();
        links = new ArrayList<String>();
        this.originalJsonData = originalJsonData;
        totalRes = new JSONArray(originalJsonData);
        contentJson=totalRes.getJSONArray(2);
        titleJson= totalRes.getJSONArray(1);
        linksJson=totalRes.getJSONArray(3);
        for (int i=0 ; i<titleJson.length();i++){
            titles.add(titleJson.getString(i).toString());
            contents.add(contentJson.getString(i).toString());
            links.add(linksJson.getString(i).toString());
        }

    }
    public List<String> getTitles() {
        return titles;
    }

    public List<String> getContents() {
        return contents;
    }

    public List<String> getLinks() {
        return links;
    }
}
