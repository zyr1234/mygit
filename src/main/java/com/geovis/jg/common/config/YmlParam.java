/*
package com.geovis.jg.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

*/
/**
 * 配置文件中的参数以及一些启动前与这些参数有关的准备
 * @author Morris
 * @date 2021/4/22 11:25
 *//*

@Component
@ConfigurationProperties(prefix = "dirs")
@Data
@Slf4j
public class YmlParam {

    private String fileBaseDir;
    private String fileZipPath;
    private String fileZipUrl;

    @PostConstruct
    public void init(){
        log.info("文件基础路径[{}]",fileBaseDir);
        if(fileBaseDir==null)
            throw new RuntimeException("fileBaseDir不能为空");
        File f = new File(fileBaseDir);
        if(f.exists()){
            if(!f.isDirectory())
                throw new RuntimeException("配置的文件路径["+fileBaseDir+"]不是文件夹");
        }else {
            f.mkdirs();
            log.info("创建文件夹[{}]",fileBaseDir);
        }

    }
}
*/
