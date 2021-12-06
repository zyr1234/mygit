package cn.com.geovis.geoiot.other;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JxJson {
    public static List<HashMap<String,Object>> getJsonList(String json){
        List<HashMap<String,Object>> dataList;
        dataList = new ArrayList<>();
      try{
          JSONObject rootObject = new JSONObject(json);
          JSONArray trajectory = rootObject.getJSONArray("trajectory");


          for(int i =0;i<trajectory.length();i++ ){
              JSONObject sonObject = trajectory.getJSONObject(i);
              //JSONArray attributes = trajectory.getJSONArray("attributes");

          }
      }catch(JSONException e){

      }


        return null;
    }

}
