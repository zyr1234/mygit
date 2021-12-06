package cn.com.geovis.geoiot.converter;

import cn.com.geovis.GeoIotAdaptor.scheduler.AdaptorRun;
import cn.com.geovis.geoiot.sender.pojo.GvEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.com.geovis.geoiot.sender.utils.Sender;
import io.siddhi.query.api.definition.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqliteDataConverter implements DataConverter{

    private static final Logger log = LoggerFactory.getLogger(SqliteDataConverter.class);

    @Override
    public Object  convertData(Object ob) {
        ResultSet rs = (ResultSet)ob;
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> valueMap = new LinkedHashMap<>();
        try {
            while (rs.next()) {
                for(int i = 0; i < AdaptorRun.fieldSourceTypeList.size(); i++){
                    String type = AdaptorRun.fieldSourceTypeList.get(i);
                    String field = AdaptorRun.fieldList.get(i);
                    switch (type){
                        case "string":
                            valueMap.put(field, rs.getString(field));
                            break;
                        case "int":
                            valueMap.put(field, String.valueOf(rs.getInt(field)));
                            break;
                        case "long":
                            valueMap.put(field, String.valueOf(rs.getLong(field)));
                            break;
                        case "double":
                            valueMap.put(field, String.valueOf(rs.getDouble(field)));
                            break;
                        case "bool":
                            valueMap.put(field, String.valueOf(rs.getBoolean(field)));
                            break;
                        case "float":
                            valueMap.put(field, String.valueOf(rs.getFloat(field)));
                            break;
                        default:
                            break;
                    }
                }
                resultList.add(valueMap);
            }

        } catch (Exception e){

        }

        return resultList;
    }

    @Override
    public void convertDataAndSend(Object ob, Sender sender) {
        ResultSet rs = (ResultSet) ob;
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> valueMap = new LinkedHashMap<>();
        int count = 0;
        String Dates = "1";
        try {
            while (rs.next()) {

                for (int i = 0; i < AdaptorRun.fieldSourceTypeList.size(); i++) {
                    String type = AdaptorRun.fieldSourceTypeList.get(i);
                    String field = AdaptorRun.fieldList.get(i);
                    if (field.equals("Date")){
                        String s = rs.getString("Date")+" "+rs.getString("Time");
                        long ut = 1275264977820L;
                        ut = ConvertUnixTime(s);
                        valueMap.put("Date",String.valueOf(ut));
                        continue;
                    }

                    switch (type) {
                        case "string":
                            if (rs.getString(field) == null){
                                valueMap.put(field,"null");
                                continue;
                            }
                            valueMap.put(field, rs.getString(field));
                            break;
                        case "int":
                            valueMap.put(field, String.valueOf(rs.getInt(field)));
                            break;
                        case "long":
                            valueMap.put(field, String.valueOf(rs.getLong(field)));
                            break;
                        case "double":
                            valueMap.put(field, String.valueOf(rs.getDouble(field)));
                            break;
                        case "bool":
                            valueMap.put(field, String.valueOf(rs.getBoolean(field)));
                            break;
                        case "float":
                            valueMap.put(field, String.valueOf(rs.getFloat(field)));
                            break;
                        default:
                            break;
                    }
                }
                count++;
                sender.send(valueMap);
            }
            log.info("本次发送数据条数为：{} ", count +" "+ valueMap);

        } catch (Exception e) {
        }finally {
            try{
                rs.close();
            }catch (Exception e){}

        }
    }
    public long ConvertUnixTime(String time){
        long unixTime = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateTime = df.parse(time);
            String t = df.format(dateTime);
            unixTime = df.parse(t).getTime();
        } catch (ParseException e) {
            log.info("ConvertUnixTime>>"+e);
        }
        return unixTime;
    }

}
