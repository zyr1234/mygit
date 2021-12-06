package com.geovis.jg.other.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConifig {
        @Bean
        public ConnectionFactory connectionFactory(){
            CachingConnectionFactory factory = new CachingConnectionFactory();
            factory.setUri("amqp://admin:admin@172.20.10.2:5672/zcnh_mb_test");
            return factory;
        }

        @Bean
        public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
            //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            return factory;
        }


}

