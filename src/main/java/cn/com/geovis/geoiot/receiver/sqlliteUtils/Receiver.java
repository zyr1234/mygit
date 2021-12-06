package cn.com.geovis.geoiot.receiver.sqlliteUtils;

public interface Receiver {

    /**
     * 初始化Receiver，如创建连接、构造对象等
     */
    void init() throws Exception;
    /**
     * 实时数据接收
     */
    Object receive(String... args) throws Exception;
    /**
     * 销毁Receiver，如删除连接、释放对象等
     */
    void destory() throws Exception;
}
