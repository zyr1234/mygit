package cn.com.geovis.geoiot.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cha {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        ResultSet rs = null;
        Statement stat = null;
        Logger log = null;
        Connection c = null;
        Map lowMap = new HashMap();
        int count = 0;
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://192.168.140.85:5431/gv_iot?useUnicode=true&characterEncoding=UTF-8", "postgres", "postgres");
        c.setAutoCommit(false);
        stat = c.createStatement();
        rs = stat.executeQuery("select traj from tttt_latest_event ");
        List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
        while (rs.next()) {
            String traj = rs.getString("traj");
            Map Xstl = JSON.parseObject(traj, Map.class);
            System.out.println(Xstl);
            Map xsb = new HashMap();
            Map lomap = new HashMap();
            Map<String, Sb> sb = new HashMap();
            for (Object key : Xstl.keySet()) {
                String s = Xstl.get(key).toString();
                JSONObject jsonObjec = JSONObject.parseObject(s);
                Map stl = JSON.parseObject(String.valueOf(jsonObjec), Map.class);
                JSONObject attributes = (JSONObject) stl.get("attributes");
                xsb = JSON.parseObject(String.valueOf(attributes), Map.class);
                Map<String,Object> gjdq = (Map<String, Object>) xsb.get("gjdq");
                for (Object k:xsb.keySet()){
                    if (!k.equals("leafcount")){
                        String s1 = k.toString();
                        Map<String,Sb> kk = (Map<String, Sb>) xsb.get(s1);
                        System.out.println(k+"    "+kk.get("value"));
                        lowMap.put(k,kk.get("value"));

                    }

                }
                ls.add(lowMap);
            }

        }
        ///System.out.println(ls);
       for (int i=0;i<ls.size();i++){
            System.out.println(ls.get(i));
        }
    }

}