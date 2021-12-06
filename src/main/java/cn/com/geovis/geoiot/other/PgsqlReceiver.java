package cn.com.geovis.geoiot.other;


import org.slf4j.Logger;

import java.sql.*;


public class PgsqlReceiver implements Pg{
    ResultSet rs = null;
    private Statement stat = null;
    Connection c =null;
    Logger log = null;

    @Override
    public void init() throws Exception {

        //log.info("客户端启动...");
        Class.forName("org.postgresql.Driver");
        c= DriverManager.getConnection("jdbc:postgresql://192.168.140.251:5432/htc-ceshi?useUnicode=true&characterEncoding=UTF-8","postgres","postgres");

    }

    public Object  receive(String... args) {

        try{

            c.setAutoCommit(false);
            System.out.println("数据库链接成功");
            stat = c.createStatement();
            rs =  stat.executeQuery("select *from hzjhyts");


        }catch(Exception e){
            e.printStackTrace();

        }


        return rs;
    }


    @Override
    public void destory() throws SQLException {
        this.c.close();
    }
}
