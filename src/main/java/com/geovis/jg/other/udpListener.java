package com.geovis.jg.other;

import com.geovis.jg.other.config.UdpServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Configuration
public class udpListener{
    @Value("${udpPort}")
    private int port;

    @Autowired
    private UdpServiceImpl udpServiceImpl;

    /*@Bean
    public UnicastReceivingChannelAdapter unicastReceivingChannelAdapter() {
        // 实例化一个UDP消息接收服务
        UnicastReceivingChannelAdapter unicastReceivingChannelAdapter = new UnicastReceivingChannelAdapter(udpPort);
        // unicastReceivingChannelAdapter.setOutputChannel(new DirectChannel());
        unicastReceivingChannelAdapter.setOutputChannelName("udpChannel");
        logger.info("UDP服务启动成功，端口号为: {}", udpPort);
        return unicastReceivingChannelAdapter;
    }*/

    @Bean
    public IntegrationFlow integrationFlow() {
        log.info("UDP服务启动成功，端口号为: {}", port);
        return IntegrationFlows.from(Udp.inboundAdapter(port)).channel("udpChannel").get();
    }

    /**
     * 转换器
     */
    @Transformer(inputChannel = "udpChannel", outputChannel = "udpFilter")
    public String transformer(@Payload byte[] payload, @Headers Map<String, Object> headers) {
        //TODO 进行数据处理
        log.info("payload,{}", Arrays.toString(payload));
        // 向客户端响应，还不知道怎么写git
        return new String(payload);
    }

    /**
     * 过滤器
     *
     */
    @Filter(inputChannel = "udpFilter", outputChannel = "udpRouter")
    public boolean filter(String message, @Headers Map<String, Object> headers) {
        // 获取来源Id
        String id = headers.get("id").toString();
        // 获取来源IP，可以进行IP过滤
        String ip = headers.get("ip_address").toString();
        // 获取来源Port
        String port = headers.get("ip_port").toString();
        // 信息数据过滤
        /*if (message.indexOf("-") < 0) {
            // 没有-的数据会被过滤
            return false;
        }*/
        return true;
    }

    /**
     * 路由分发处理器
     *
     */
    @Router(inputChannel = "udpRouter")
    public String router(String message, @Headers Map<String, Object> headers) {
        // 获取来源Id
        String id = headers.get("id").toString();
        // 获取来源IP，可以进行IP过滤
        String ip = headers.get("ip_address").toString();
        // 获取来源Port
        String port = headers.get("ip_port").toString();
        // 筛选，走那个处理器
        /* if (false) {
            return "udpHandle2";
        }*/
        return "udpHandle1";
    }

    /**
     * 最终处理器1
     */
    @ServiceActivator(inputChannel = "udpHandle1")
    public void udpMessageHandle(String message) throws Exception {
        // 可以进行异步处理
        udpServiceImpl.udpHandleMethod(message);
        log.info("UDP1:" + message);
    }

    /**
     * 最终处理器2
     *
     */
    @ServiceActivator(inputChannel = "udpHandle2")
    public void udpMessageHandle2(String message) throws Exception {
        log.info("UDP2:" + message);
    }

}



