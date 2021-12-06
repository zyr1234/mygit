package cn.com.geovis.GeoIotAdaptor.utils;

import cn.com.geovis.geoiot.sender.pojo.GvEvent;
import cn.com.geovis.geoiot.sender.utils.Sender;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Binary {
    private static final Logger log = LoggerFactory.getLogger(Binary.class);
    static long countNum = 0L;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Binary() {
    }

    public static void sendBinary(Sender tcpSender, ResultSet rs, long searchTime) {
        int count = 0;
        GvEvent gvEvent = new GvEvent();

        try {
            while(rs.next()) {
                if (rs.getDouble("LATITUDE") != 0.0D && rs.getDouble("LONGITUDE") != 0.0D) {
                    ++count;
                    // 源id
                    gvEvent.setSource_id("1");
                    gvEvent.setTarget_type("5");
                    gvEvent.setTarget_id(rs.getString("SIMID"));
                    String name = rs.getString("CAR_ID");
                    gvEvent.setTarget_name(name);
                    gvEvent.setTarget_time(searchTime);
                    gvEvent.setLat(rs.getDouble("LATITUDE"));
                    gvEvent.setLon(rs.getDouble("LONGITUDE"));
                    gvEvent.setExtend(getJsonStr());
                    gvEvent.setAlt(0.0D);
                    gvEvent.setHeading(0.0D);
                    tcpSender.send(gvEvent);
                }
            }

            countNum += (long)count;
            log.info("当前数据时间：" + format.format(new Date(searchTime)) + "     当前条数" + count + "    总条数：" + countNum);
            System.out.println();
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException var14) {
                var14.printStackTrace();
            }

        }

    }

    public static String getJsonStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"content\":").append("conteng");
        sb.append(",\"totalElements\":").append(3);
        sb.append(",\"last\":").append(true);
        sb.append(",\"totalPages\":").append(3);
        sb.append(",\"size\":").append(3);
        sb.append(",\"number\":").append(5);
        sb.append(",\"numberOfElements\":").append(4);
        sb.append(",\"first\":").append(true);
        sb.append(",\"currentPage\":").append(5);
        sb.append("}");
        String jsonStr = sb.toString();
        return jsonStr;
    }
}
