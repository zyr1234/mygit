package com.geovis.jg.other;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ配置类
 */
@Component
public class MyRabbitListener {

    @RabbitListener(queues="zcnh_mb_test_queues")
    public void receiveSimulateMsg(Map data){
        System.out.println("RabbitMQ MSG:"+data);
        //TODO 这里可以做异步的工作
    }
}
