package cn.com.geovis.geoiot.other;

import java.sql.SQLException;

public interface Pg {


        /**
         * 初始化Receiver，如创建连接、构造对象等
         */
        void init() throws Exception;

        Object receive(String... args) ;
        /**
         * 销毁Receiver，如删除连接、释放对象等
         */
        /**
         * 销毁Receiver，如删除连接、释放对象等
         */
        void destory() throws SQLException;


}
