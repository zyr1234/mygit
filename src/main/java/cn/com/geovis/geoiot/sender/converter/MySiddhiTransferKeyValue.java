package cn.com.geovis.geoiot.sender.converter;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class MySiddhiTransferKeyValue {
    private String sessionId = UUID.randomUUID().toString() + "-" + "192.168.44.73" + ":" + "9892";

    public byte[] convertKeyValue(Map<String, String> data, String channelId) throws IOException {
        return dataEncoder(data, channelId);
    }

    private byte[] dataEncoder(Map<String, String> data, String chanelId) throws IOException {
        StringBuilder convertStr = new StringBuilder(64);

        Iterator<Entry<String, String>> ite = data.entrySet().iterator();
        while(ite.hasNext()){
            Entry<String, String> entry = ite.next();
            convertStr.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }

        byte[] message = convertStr.toString().substring(0, convertStr.toString().length() - 1).getBytes(StandardCharsets.UTF_8);;
        return covertSendMeg(chanelId, message);

    }

    private byte[] covertSendMeg(String channelId,byte[] sendBytes)  {
        int dataLength = sendBytes.length;
        int messageSize = 4 + sessionId.length() + 4 + channelId.length() + 4 + dataLength;

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte((byte) 2);  //1
        byteBuf.writeInt(messageSize); //4
        byteBuf.writeInt(sessionId.length()); //4
        byteBuf.writeBytes(sessionId.getBytes(StandardCharsets.UTF_8));
        byteBuf.writeInt(channelId.length()); //4
        byteBuf.writeBytes(channelId.getBytes(StandardCharsets.UTF_8));
        byteBuf.writeInt(dataLength); //4
        byteBuf.writeBytes(sendBytes);
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        byteBuf.clear();
        return req;
    }

}
