package cn.com.geovis.geoiot.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class chaxun {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://192.168.140.85:5431/gv_iot?useUnicode=true&characterEncoding=UTF-8", "postgres", "postgres");
        conn.setAutoCommit(false);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select traj from tttt_latest_event ");

        List<Map<String,Object>> list = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object>  sj = new HashMap<>();
            String traj = rs.getString("traj");
            JSONObject jb = JSONObject.parseObject(traj);
            Map<String,Map<String,Object>> map = JSON.parseObject(String.valueOf(jb), Map.class);
            Map<String, Object> trajectory = map.get("trajectory");

            JSONObject attributes = (JSONObject) trajectory.get("attributes");
            Map<String,Object> data = JSONObject.parseObject(String.valueOf(attributes), Map.class);

            for (String key:data.keySet()){
                if (!key.equals("leafcount")){
                    String value = JSON.toJSONString(data.get(key));
                    Sb sb = JSONObject.parseObject(value, Sb.class);
                    sj.put(key,sb.getValue());
                }
            }

            list.add(sj);
        }

        System.out.println(list);

    }
}
