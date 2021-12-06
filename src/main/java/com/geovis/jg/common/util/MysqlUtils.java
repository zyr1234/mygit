package com.geovis.jg.common.util;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlUtils {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		Connection con;
		
		String driver = "com.mysql.jdbc.Driver";
		
		String url = "jdbc:mysql://15.1.10.50:3306/szdqxm";
		
		String user = "iecas";
		
		String password = "Iecas_12";
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if(!con.isClosed())
				System.out.println("Succeeded connecting to the Database");
			Statement stat1 = con.createStatement();
			String sql1 = "select * from image_meta t where t.IMAGE_NAME REGEXP '[0-9]{14}'";
			ResultSet rs = stat1.executeQuery(sql1);
			Integer id = null;
			String image_name = null;
			String update_time = null;
			while(rs.next()){
				id = rs.getInt("id");
				image_name = rs.getString("image_name");
//				System.out.println(id + " " + image_name);
				update_time = MysqlUtils.getUpdateTime(image_name);
//				System.out.println(update_time);
				String sql2 = "update mb_yx set UPDATE_TIME="+update_time+" where YXID="+id;
				stat1.addBatch(sql2);
			}
			stat1.executeBatch();
			rs.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String getUpdateTime(String image_name){
		String regex = "[0-9]{14}";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(image_name);
		while(m.find()){
			return m.group();
		}
		return "";
	}
}
