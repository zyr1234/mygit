package cn.com.geovis.geoiot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit MQ配置类
 */
@Configuration
public class rabbitConfig {
    //交换机名称
    public static final String ITEM_TOPIC_EXCHANGE = "zcnh_mb_test_exchange";
    //队列名称
    public static final String ITEM_QUEUE = "zcnh_mb_test_queue";

    //声明交换机
    @Bean("zcnh_mb_test_exchange")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    //声明队列
    @Bean("zcnh_mb_test_queue")
    public Queue itemQueue(){
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding itemQueueExchange(@Qualifier("zcnh_mb_test_queue") Queue queue,
                                     @Qualifier("zcnh_mb_test_exchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("zcnh.#").noargs();
    }
}
