package cn.com.geovis.geoiot.sender.converter;

import io.siddhi.query.api.definition.Attribute;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MyBinaryMessageConverterUtil {

    private MyBinaryMessageConverterUtil() {
    }

    public static byte[] loadData(InputStream in, byte[] dataArray) throws IOException {
        int start = 0;

        do {
            int readCount = in.read(dataArray, start, dataArray.length - start);
            if (readCount == -1) {
                throw new EOFException("Connection closed from remote end.");
            }

            start += readCount;
        } while (start != dataArray.length);

        return dataArray;
    }

    public static String getString(ByteBuffer byteBuffer, int size) throws UnsupportedEncodingException {
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes);
        return new String(bytes, Charset.forName("UTF-8"));
    }

    /**
     * 将string进行utf8的编码
     * @param data
     * @param type
     * @return
     */
    public static int getSize(Object data, Attribute.Type type) {
        switch (type) {
            case STRING:
                return 4 + ((String) data).getBytes(Charset.forName("UTF-8")).length;
            case INT:
                return 4;
            case LONG:
                return 8;
            case FLOAT:
                return 4;
            case DOUBLE:
                return 8;
            case BOOL:
                return 1;
            default:
                return 4;
        }
    }

    public static void assignData(Object data1, ByteBuffer eventDataBuffer, Attribute.Type type) throws IOException {
        String data = (String)data1;
        switch (type) {
            case STRING:
                eventDataBuffer.putInt(((String) data).getBytes(Charset.defaultCharset()).length);
                eventDataBuffer.put(((String) data).getBytes(Charset.defaultCharset()));
                break;
            case INT:
                eventDataBuffer.putInt(Integer.valueOf(data));
                break;
            case LONG:
                eventDataBuffer.putLong(Long.valueOf(data));
                break;
            case FLOAT:
                eventDataBuffer.putFloat(Float.valueOf(data));
                break;
            case DOUBLE:
                eventDataBuffer.putDouble(Double.valueOf(data));
                break;
            case BOOL:
                eventDataBuffer.put((byte) (Boolean.valueOf(data) ? 1 : 0));
                break;
            case OBJECT:
                eventDataBuffer.putInt(0);
        }

    }

}
