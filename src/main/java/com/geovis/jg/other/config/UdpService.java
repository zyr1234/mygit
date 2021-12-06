package com.geovis.jg.other.config;

import org.springframework.scheduling.annotation.Async;

public interface UdpService {
    @Async("threadPoolTaskExecutor")
    void udpHandleMethod(String message) throws Exception;
}
