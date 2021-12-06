package com.geovis.jg.other.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UdpServiceImpl implements UdpService {

        @Override
        @Async("threadPoolTaskExecutor")
        public void udpHandleMethod(String message) throws Exception {
            log.info("业务开始处理");
            Thread.sleep(3000);
            log.info("业务处理完成");
        }

}
