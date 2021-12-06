//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.com.geovis.GeoIotAdaptor.scheduler;

import cn.com.geovis.GeoIotAdaptor.utils.ResourceUtil;
import cn.com.geovis.geoiot.config.rabbitConfig;
import cn.com.geovis.geoiot.converter.DataConverter;
import cn.com.geovis.geoiot.converter.SqliteDataConverter;
import cn.com.geovis.geoiot.receiver.utils.Jxmnq;
import cn.com.geovis.geoiot.sender.utils.Sender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import io.siddhi.query.api.definition.Attribute;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AdaptorRuntcp {
    private static final Logger log = LoggerFactory.getLogger(AdaptorRuntcp.class);
    private String jdbcURL;
    @Value("${replay.speed}")
    private int speed;

    @Value("${db.sqlite.path}")
    private String sqliteFilePath;


    @Autowired
    private Sender sender;
    @Autowired
    RabbitTemplate rabbitTemplate;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Connection conn = null;
    private Statement stat = null;

    public static List<String> fieldList = new ArrayList<>();
    public static List<String> fieldSourceTypeList = new ArrayList<>();
    public static List<Attribute.Type> fieldSiddhiTypeList = new ArrayList<>();

    public AdaptorRuntcp() {
    }

    @PostConstruct
    public void runAdapter() throws Exception {
        ResultSet rs = null;
        init();
        Jxmnq jxmnq = new Jxmnq();
        DataConverter converter = new SqliteDataConverter();

        while (true) {

            Map oj = (Map) jxmnq.receive();

            if (oj != null) {
                // rs = (ResultSet) oj;
                sender.send(oj);
                rabbitTemplate.convertAndSend(rabbitConfig.ITEM_TOPIC_EXCHANGE,"zcnh.mb_routingKey",oj);
                //converter.convertDataAndSend(rs, sender);
            }
        }
    }

    private void init() throws IOException {
        File file = ResourceUtil.extractFileToTempDir("/fieldDefinition.properties");
        String filePath = file.getPath();

        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

        for(String line : lines){
            if(StringUtils.isBlank(line) || line.startsWith("#")){
                continue;
            }
            String[] lineArr = line.split(":");
            if(lineArr.length != 2 || StringUtils.isBlank(lineArr[0]) || StringUtils.isBlank(lineArr[1])){
                continue;
            }
            fieldList.add(lineArr[0].trim());
            fieldSourceTypeList.add(lineArr[1].trim().toLowerCase());
        }

        initFiledSiddhiType();

    }

    private void initFiledSiddhiType(){
        for(String type : fieldSourceTypeList){
            String lowerCaseType = type.toLowerCase();
            switch (lowerCaseType){
                case "string":
                    fieldSiddhiTypeList.add(Attribute.Type.STRING);
                    break;
                case "int":
                    fieldSiddhiTypeList.add(Attribute.Type.INT);
                    break;
                case "long":
                    fieldSiddhiTypeList.add(Attribute.Type.LONG);
                    break;
                case "double":
                    fieldSiddhiTypeList.add(Attribute.Type.DOUBLE);
                    break;
                case "bool":
                    fieldSiddhiTypeList.add(Attribute.Type.BOOL);
                    break;
                case "float":
                    fieldSiddhiTypeList.add(Attribute.Type.FLOAT);
                    break;
                case "object":
                    fieldSiddhiTypeList.add(Attribute.Type.OBJECT);
                    break;
                default:
                    break;
            }

        }
    }

}
