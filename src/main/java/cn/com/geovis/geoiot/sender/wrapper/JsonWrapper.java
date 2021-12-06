package cn.com.geovis.geoiot.sender.wrapper;

import cn.com.geovis.geoiot.sender.converter.MySiddhiTransferJson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@ConditionalOnProperty(value = "replay.format", havingValue = "json", matchIfMissing = true)
public class JsonWrapper implements Wrapper {

    private MySiddhiTransferJson mySiddhiTransfer = new MySiddhiTransferJson();

    @Override
    public Object wrapperData(Object data, String... args) throws IOException {
        return mySiddhiTransfer.convertJson((Map<String, String>)data, args[0]);
    }



}
