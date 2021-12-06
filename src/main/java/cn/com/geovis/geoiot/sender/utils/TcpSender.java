package cn.com.geovis.geoiot.sender.utils;

import cn.com.geovis.geoiot.sender.converter.MySiddhiTransfer;
import cn.com.geovis.geoiot.sender.pojo.GvEvent;
import cn.com.geovis.geoiot.sender.wrapper.Wrapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@ConditionalOnProperty(value = "replay.protocol", havingValue = "tcp", matchIfMissing = true)
public class TcpSender extends Sender {

    @Value("${replay.ip}")
    private String ip;

    @Value("${replay.port}")
    private Integer port;

    @Value("${replay.channelId}")
    private String channelId;

    private Socket socket;
    private Boolean isInit =false;

    @Autowired
    private Wrapper wrapper;

    private MySiddhiTransfer mySiddhiTransfer;

    @Override
    @PostConstruct
    public void init() throws IOException {
//        socket = new Socket(ip, port);
        mySiddhiTransfer = new MySiddhiTransfer();
        isInit=true;
    }

    @Override
    public void send(GvEvent event) throws Exception {
        if(!isInit){
            throw new Exception("请先执行init方法！");
        }
        if(event.checkValidate()!= GvEvent.ValidateField.pass){
            return;
        }
        byte[] transData = mySiddhiTransfer.convertBinary(event,channelId);
        OutputStream out = socket.getOutputStream();
        out.write(transData);
        out.flush();
    }

    @Override
    public void destory() throws IOException {
        if(socket!=null){
            socket.close();
        }
    }

    @Override
    public void send(Map<String, String> map) throws Exception {
        if (socket == null) {
            socket = new Socket(ip, port);
        }
        if(!isInit){
            throw new Exception("请先执行init方法！");
        }

        byte[] transData = (byte[]) wrapper.wrapperData(map, channelId);
        OutputStream out = socket.getOutputStream();
        out.write(transData);
        out.flush();
        if(socket!=null){
            socket.close();
        }
    }
}
