package com.jdkj;

import com.jdkj.config.AtalsBridgeCenterConfigurations;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;


@SpringBootApplication
@EnableKafkaStreams
@EnableConfigurationProperties(AtalsBridgeCenterConfigurations.class)
public class KafkaStreamSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamSpringBootApplication.class,args);
    }
    /**
     * 创建AtalsClient实例
     * @param config
     * @return
     */
    @Bean(destroyMethod = "close")
    public AtlasClientV2 clientV2(AtalsBridgeCenterConfigurations config) throws AtlasException {
        return new AtlasClientV2(config.getAtlasServers(),new String[]{config.getUser(),config.getPassword()});
    }
}
