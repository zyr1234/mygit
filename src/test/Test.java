
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@SpringBootTest
public class Test {
    @org.junit.Test
    public void receiveUdpTest() throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket(8081);
            while (true) {
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                log.info("=======端口 {} 等待接收消息 ======", 8081);
                socket.receive(packet);
                //TODO 需要根据报文规则添加解密操作
                String msg = new String(packet.getData(), 0, packet.getLength());
                String ip = packet.getAddress().getHostAddress();
                log.info("=========接收到来自" + ip + ":" + 8081 + "的消息:" + msg);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void send() {
        try {
            File file = ResourceUtils.getFile("E:\\project\\zcnh\\testFile.txt");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            //StringBuilder content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                //content.append(line);
                DatagramSocket datagram;
                DatagramPacket packet;
                InetAddress inetAddress = InetAddress.getByName("172.20.10.2");
                int port = 8081;
                datagram = new DatagramSocket();
                byte[] data = line.getBytes();
               // byte[] data = content.toString().getBytes();
                packet = new DatagramPacket(data, data.length, inetAddress, port);
                log.info("data:",data);
                datagram.send(packet);
                //warning: Call to 'Thread.sleep()' in a loop, probably busy-waiting
                Thread.sleep(1000);
                datagram.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
