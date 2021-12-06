package com.geovis.jg.common.util;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 数据库生成实体类相关的一些方法
 * @author Morris
 * @date 2021/4/23 10:03
 */
public class CreateSql {

    public static String pgTable2JavaFile(DataSource dataSource,String tableName,String packageName){
        List<Map<String, Object>> tmp = new JdbcTemplate(dataSource).queryForList(
                "select * from information_schema.columns where table_name ='"+tableName+"'");
        StringBuilder builder = new StringBuilder()
                .append("package ").append(packageName).append("; \n")
                .append("import lombok.Data;\nimport java.util.Date;\n")
                .append("@Data\npublic class ")
                .append(className(tableName)).append("{ \n");
        for(Map<String,Object> i: tmp){
            builder.append("\n    ").append("private ")
                    .append(checkType((String) i.get("data_type"))).append(" ")
                    .append(camleCase((String) i.get("column_name"))).append(";")
                    .append("\n");
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * 表名生成类名
     * @author Morris
     * @date 2021/4/23 10:04
     */
    public static String className(String tableName) {
        int i = tableName.indexOf("_");
        if(i==-1)
            return tableName;
        char[] fs = tableName.substring(0,i).toCharArray();
        fs[0]-=32;
        tableName = tableName.substring(i);
        return String.valueOf(fs) + camleCase(tableName);
    }

    /**
     * 将表字段名转换为驼峰命名
     * @author Morris
     * @date 2021/4/23 10:04
     */
    private static String camleCase(String column_name) {
        StringBuilder builder = new StringBuilder();
        String[] seg = column_name.split("_");
        builder.append(seg[0]);
        for(int i=1;i<seg.length;i++){
            char[] s = seg[i].toCharArray();
            s[0] -=32;
            builder.append(new String(s));
        }

        return builder.toString();
    }

    /**
     * 跟据表字段类型筛选java类型
     * //todo 类型未必全面
     * @author Morris
     * @date 2021/4/23 10:04
     */
    private static String checkType(String data_type) {
        switch (data_type){
            case "integer":
                return "Integer";
            case "numeric":
                return "Double";
            case "timestamp without time zone":
                return "Date";
            case "character varying":
                return "String";
            case "boolean":
                return "Boolean";
            default:
                throw new RuntimeException("未知类"+data_type);
        }
    }

    /**
     * 生成表明对应的insert语句
     * @author Morris
     * @date 2021/4/23 10:06
     */
    public static String createSql(DataSource dataSource,String tableName) {

        List<Map<String, Object>> tmp = new JdbcTemplate(dataSource).queryForList(
                "select * from information_schema.columns where table_name ='"+tableName+"'");
        StringBuilder insertInto = new StringBuilder("insert into ").append(tableName).append(" (");
        StringBuilder updateSql = new StringBuilder("update ").append(tableName).append(" set ");
        StringBuilder updateWhere = new StringBuilder(" where ");
        StringBuilder params = new StringBuilder();
        boolean start = true;
        for(Map<String,Object> i: tmp) {
            if(!start){
                insertInto.append(",");
                updateSql.append((String) i.get("column_name"))
                        .append("=#{").append(camleCase((String) i.get("column_name")))
                        .append(",jdbcType=").append(checkJdbcType((String) i.get("data_type"))).append("},");
            }else {
                start = false;
                updateWhere.append((String) i.get("column_name"))
                        .append("=#{").append(camleCase((String) i.get("column_name")))
                        .append(",jdbcType=").append(checkJdbcType((String) i.get("data_type"))).append("}");
            }
            insertInto.append((String) i.get("column_name"));
            params.append("#{").append(camleCase((String) i.get("column_name")))
                    .append(",jdbcType=").append(checkJdbcType((String) i.get("data_type"))).append("}");
        }
        insertInto.append(")").append("value(").append(params).append(")");
        return insertInto.append("\n\n").append(updateSql).append(updateWhere).toString();
    }

    private static String checkJdbcType(String data_type) {
        switch (data_type){
            case "integer":
                return "NUMERIC";
            case "numeric":
                return "NUMERIC";
            case "timestamp without time zone":
                return "DATE";
            case "character varying":
                return "VARCHAR";
            case "boolean":
                return "BOOLEAN";
            case "double precision":
                return "DOUBLE";
            case "bigint":
                return "BIGINT";
            default:
                throw new RuntimeException("未知类"+data_type);
        }
    }


}
