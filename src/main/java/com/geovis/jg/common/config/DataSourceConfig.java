package com.geovis.jg.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages =  {"com.geovis.jg.mb.mapper","com.geovis.jg.common.mapper"}, sqlSessionTemplateRef = "mbSqlSessionTemplate")
public class DataSourceConfig {

    @Bean(name = "mbDataSource")
    @ConfigurationProperties(prefix = "datasource.mb")
    @Primary
    public DataSource zcqbDataSource(){
//        return DataSourceBuilder.create().build();
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="mbSqlSessionFactory")
    @Primary
    public SqlSessionFactory zcqbSqlSessionFactory(@Qualifier("mbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean b = sfbSettings(dataSource);
//        b.setMapperLocations(new PathResource("classpath:pers/morris/dao/zcqb/*.java"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseColumnLabel(true);          //使用列别名替换列名
        configuration.setMapUnderscoreToCamelCase(true);    //自动使用驼峰命名属性映射字段(必须设置否则带下划线的表字段无法自动映射到驼峰命名的类字段上）
        b.setConfiguration(configuration);
        return b.getObject();
    }

    @Bean(name = "mbTransactionManager")
    @Primary
    public DataSourceTransactionManager zcqbTransactionManager(@Qualifier("mbDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate zcqbSqlSessionTemplate(@Qualifier("mbSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public static SqlSessionFactoryBean sfbSettings(DataSource dataSource){
        SqlSessionFactoryBean b = new SqlSessionFactoryBean();
        b.setDataSource(dataSource);
        //驼峰字段相关设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseColumnLabel(true);
        configuration.setMapUnderscoreToCamelCase(true);
        b.setConfiguration(configuration);
        return b;
    }
}
