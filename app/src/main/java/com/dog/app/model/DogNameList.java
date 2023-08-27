package com.dog.app.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DogNameList {
    private ArrayList<String> mDogList = new  ArrayList<String>();
public DogNameList(JSONObject data) {
    parseResponse(data);
}

public List<String> getDogList() {
    return mDogList;
}


    private   void parseResponse(JSONObject data) {

        if (data != null) {
            Iterator<String> it = data.keys();
            while (it.hasNext()) {
                String key = it.next();
                try {
                    if (data.get(key) instanceof JSONArray) {
                        JSONArray array = data.getJSONArray(key);
                        int size = array.length();
                        if(size!=0) {
                            for (int i = 0; i < size; i++) {
                                String name = array.getString(i);
                                mDogList.add(key+"/"+name);
                            }
                        } else {
                            mDogList.add(key);
                        }
                    } else if (data.get(key) instanceof JSONObject) {
                        parseResponse(data.getJSONObject(key));
                    } else {
                        System.out.println(key + ":" + data.getString(key));
                    }
                } catch (Throwable e) {
                    try {
                        System.out.println(key + ":" + data.getString(key));
                    } catch (Exception ee) {

                    }
                    e.printStackTrace();

                }
            }
        }
    }


}
