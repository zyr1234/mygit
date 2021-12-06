package cn.com.geovis.geoiot.sender.utils;

import cn.com.geovis.geoiot.sender.pojo.GvEvent;
import cn.com.geovis.geoiot.sender.wrapper.Wrapper;

import java.io.IOException;
import java.util.Map;


public abstract class Sender {


    public Wrapper wrapper;
    /**
     * 初始化Sender，如创建连接、构造对象等
     */
    public abstract void init() throws Exception;
    /**
     * 实时数据发送
     */
    public abstract void send(GvEvent event) throws Exception;
    /**
     * 销毁Sender，如删除连接、释放对象等
     */
    public abstract void destory() throws Exception;
    /**
     * 实时数据发送,按照map格式
     */
    public abstract void send(Map<String, String> map) throws Exception;

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }


}
