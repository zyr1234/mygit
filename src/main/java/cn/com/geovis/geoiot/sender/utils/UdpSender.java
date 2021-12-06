package cn.com.geovis.geoiot.sender.utils;

import cn.com.geovis.geoiot.sender.converter.MySiddhiTransfer;
import cn.com.geovis.geoiot.sender.pojo.GvEvent;
import cn.com.geovis.geoiot.sender.wrapper.Wrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

/**
 * @author zhaopy
 */
@Data
@Component
@ConditionalOnProperty(value = "replay.protocol", havingValue = "udp")
public class UdpSender extends Sender {

    @Value("${replay.ip}")
    private String ip;

    @Value("${replay.port}")
    private Integer port;

    @Value("${replay.channelId}")
    private String channelId;

    private DatagramSocket datagramSocket;
    private InetAddress ipAddr;

    private MySiddhiTransfer mySiddhiTransfer;
    private Boolean isInit =false;

    @Autowired
    private Wrapper wrapper;

    @Override
    @PostConstruct
    public void init() throws IOException {
        datagramSocket = new DatagramSocket();
        mySiddhiTransfer = new MySiddhiTransfer();
        ipAddr = InetAddress.getByName(ip);
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
        DatagramPacket dp = new DatagramPacket(transData, transData.length, ipAddr, port);
        datagramSocket.send(dp);
    }

    @Override
    public void destory() throws IOException {
        if(datagramSocket!=null){
            datagramSocket.close();
        }
    }

    @Override
    public void send(Map<String, String> map) throws Exception {
        if(!isInit){
            throw new Exception("请先执行init方法！");
        }

        byte[] transData = (byte[]) wrapper.wrapperData(map, channelId);

        //byte[] transData = mySiddhiTransfer.convertBinary(map, channelId);
        DatagramPacket dp = new DatagramPacket(transData, transData.length, ipAddr, port);
        datagramSocket.send(dp);
    }
}
