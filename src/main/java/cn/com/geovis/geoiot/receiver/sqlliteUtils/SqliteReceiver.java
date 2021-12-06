package cn.com.geovis.geoiot.receiver.sqlliteUtils;

import cn.com.geovis.GeoIotAdaptor.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SqliteReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(SqliteReceiver.class);
    private int speed;
    private String sqliteFilePath;
    private String jdbcURL;
    private long  dataMaxTime;
    private long dataMinTime;
    private long sysTime;
    private long timeGap;
    private long searchTime;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Connection conn = null;
    private Statement stat = null;

    public SqliteReceiver(){
    }

    public SqliteReceiver(String sqliteFilePath, int speed){
        this.sqliteFilePath = sqliteFilePath.startsWith("/") ? sqliteFilePath : "/" + sqliteFilePath;
        this.speed = speed;
    }

    @Override
    public void init() throws Exception {

        log.info("客户端启动...");

        try {
            Class.forName("org.sqlite.JDBC");
            log.info("数据库驱动加载成功");
        } catch (ClassNotFoundException var3) {
            log.error("");
            throw new RuntimeException("数据库驱动加载失败");
        }

        //File dbFile = ResourceUtil.extractFileToTempDir("/db/taxi.sqlite");
        //String sqliteFile = this.getClass().getClassLoader().getResource("db/taxi.sqlite").getPath();
        File dbFile = ResourceUtil.extractFileToTempDir(this.sqliteFilePath);
        String sqliteFile = this.getClass().getClassLoader().getResource(this.sqliteFilePath.substring(1)).getPath();

        if (!dbFile.exists()) {
            throw new RuntimeException("sqlite文件不存在");
        } else {
            log.info("数据文件加载成功：" + sqliteFile);
            this.jdbcURL = sqliteFile;
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
            log.info("数据库连接成功");
            this.stat = this.conn.createStatement();
            log.info("开始查询数据库最大与最小时间");
            System.out.println(this.dataMinTime);
            this.dataMinTime = 1439424001000L;
            this.dataMaxTime = 1439425201000L;
            this.sysTime = System.currentTimeMillis();
            this.timeGap = this.sysTime - this.dataMinTime;
            this.searchTime = this.dataMinTime;

        }
    }


    @Override
    public Object receive(String... args) throws Exception {

        ResultSet rs = null;

        Long newGap = System.currentTimeMillis() - this.dataMaxTime;
        if (newGap < this.timeGap && this.searchTime < this.dataMaxTime) {
            long tmpTime = System.currentTimeMillis();
            if (tmpTime - this.sysTime >= (long)(1000 / this.speed)) {
                this.sysTime = tmpTime;
                this.searchTime += 1000L;
                String searchTimeString = this.format.format(new Date(this.searchTime));
                //String sql = "select *  from test '" + searchTimeString + "'";
                String sql = "select *  from hz";
                rs = this.stat.executeQuery(sql);

            }
        } else {
            this.timeGap = System.currentTimeMillis() - this.dataMinTime;
            this.searchTime = this.dataMinTime;
        }

        return  rs;

    }

    @Override
    public void destory() throws Exception {
        this.conn.close();
    }
}
