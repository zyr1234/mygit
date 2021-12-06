package cn.com.geovis.geoiot.sender.wrapper;

import java.io.IOException;

public interface Wrapper {

    public Object wrapperData(Object data, String... args) throws IOException;
}
