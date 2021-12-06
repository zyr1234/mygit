package cn.com.geovis.geoiot.sender.converter;

import cn.com.geovis.GeoIotAdaptor.scheduler.AdaptorRun;
import cn.com.geovis.geoiot.sender.pojo.GvEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.siddhi.core.event.Event;
import io.siddhi.query.api.definition.Attribute;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class MySiddhiTransfer {
    private String sessionId = UUID.randomUUID().toString() + "-" + "192.168.44.73" + ":" + "9892";


    //TODO: 对于同一个app下面的stream，types是固定的；如果更改了stream，一定记得修改types；
    private Attribute.Type[] types = new Attribute.Type[]{Attribute.Type.STRING, Attribute.Type.STRING, Attribute.Type.STRING, Attribute.Type.STRING, Attribute.Type.LONG, Attribute.Type.DOUBLE, Attribute.Type.DOUBLE,Attribute.Type.DOUBLE,Attribute.Type.DOUBLE, Attribute.Type.STRING};

    public MySiddhiTransfer(){
    }

    public byte[] convertBinary(GvEvent data, String channelId) throws IOException {

        GvEvent.ValidateField validateField = data.checkValidate();
        if (validateField.getFieldNum() == 0) {
            return dataEncoderBinary(data,channelId);
        } else {
            System.out.println(validateField.getFieldMsg());
            return null;
        }
    }

    private byte[] dataEncoderBinary(GvEvent data, String chanelId) throws IOException {
        ArrayList<Event> arrayList = new ArrayList<Event>(1);
        arrayList.add(new Event(System.currentTimeMillis(), new Object[]{data.getSource_id(), data.getTarget_type(), data.getTarget_id(), data.getTarget_name(), data.getTarget_time(), data.getLon(), data.getLat(), data.getAlt(),data.getHeading(),data.getExtend()}));
        byte[] message = MyBinaryEventConverter.convertToBinaryMessage(
                arrayList.toArray(new Event[1]), types).array();

        return covertSendMeg(chanelId,message);
    }

    private byte[] covertSendMeg(String channelId,byte[] sendBytes) throws UnsupportedEncodingException {
        int dataLength = sendBytes.length;
        int messageSize = 4 + sessionId.length() + 4 + channelId.length() + 4 + dataLength;

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte((byte) 2);  //1
        byteBuf.writeInt(messageSize); //4
        byteBuf.writeInt(sessionId.length()); //4
        byteBuf.writeBytes(sessionId.getBytes("UTF-8"));
        byteBuf.writeInt(channelId.length()); //4
        byteBuf.writeBytes(channelId.getBytes("UTF-8"));
        byteBuf.writeInt(dataLength); //4
        byteBuf.writeBytes(sendBytes);
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        byteBuf.clear();
        return req;
    }

    public byte[] convertBinary(Map<String, String> data, String channelId) throws IOException {
        List<Attribute.Type> types = AdaptorRun.fieldSiddhiTypeList;
        return dataEncoderBinary(data, channelId, types);
    }

    private byte[] dataEncoderBinary(Map<String, String> data, String chanelId, List<Attribute.Type> types) throws IOException {
        ArrayList<Event> arrayList = new ArrayList<Event>(1);
        int dataLength = data.size();
        Object[] ob = new Object[dataLength];
        Iterator<Map.Entry<String, String>> ite = data.entrySet().iterator();
        int index = 0;
        while (ite.hasNext()){
            Map.Entry<String, String> entry = ite.next();
            ob[index] = entry.getValue();
            index++;
        }

        arrayList.add(new Event(System.currentTimeMillis(), ob));
        byte[] message = MyBinaryEventConverter.convertToBinaryMessage(
                arrayList.toArray(new Event[1]), types.toArray(new Attribute.Type[0])).array();

        return covertSendMeg(chanelId,message);
    }

}
