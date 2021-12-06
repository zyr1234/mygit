package cn.com.geovis.geoiot.sender.converter;

import io.siddhi.core.event.Event;
import io.siddhi.query.api.definition.Attribute;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MyBinaryEventConverter {
    public MyBinaryEventConverter() {
    }
    public static ByteBuffer convertToBinaryMessage(Event[] events, Attribute.Type[] types) throws IOException {
        int eventCount = events.length;
        int messageSize = 4;
        Event[] var4 = events;
        int var5 = events.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            Event event = var4[var6];
            messageSize += getEventSize(event, types);
        }

        ByteBuffer messageBuffer = ByteBuffer.wrap(new byte[messageSize]);
        messageBuffer.putInt(eventCount);
        Event[] var13 = events;
        var6 = events.length;

        for(int var14 = 0; var14 < var6; ++var14) {
            Event event = var13[var14];
            messageBuffer.putLong(event.getTimestamp());
            if (event.getData() != null && event.getData().length != 0) {
                Object[] data = event.getData();

                for(int i = 0; i < data.length; ++i) {
                    Object aData = data[i];
                    MyBinaryMessageConverterUtil.assignData(aData, messageBuffer, types[i]);
                }
            }
        }

        return messageBuffer;
    }

    private static int getEventSize(Event event, Attribute.Type[] types) {
        int eventSize = 8;
        Object[] data = event.getData();
        if (data != null) {
            for(int i = 0; i < data.length; ++i) {
                Object aData = data[i];
                eventSize += MyBinaryMessageConverterUtil.getSize(aData, types[i]);
            }
        }

        return eventSize;
    }
}
