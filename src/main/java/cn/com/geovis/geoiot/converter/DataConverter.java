package cn.com.geovis.geoiot.converter;

import cn.com.geovis.geoiot.sender.utils.Sender;

public interface DataConverter {

    public Object convertData(Object ob);
    public void convertDataAndSend(Object ob, Sender sender);
}
