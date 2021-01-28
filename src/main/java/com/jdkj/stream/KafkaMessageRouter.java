package com.jdkj.stream;

import com.jdkj.config.AtalsBridgeCenterConfigurations;
import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
@Component
public class KafkaMessageRouter {
    @Bean
    public KStream<String, String> kStream1(StreamsBuilder streamBuilder, AtalsBridgeCenterConfigurations config) {

        KStream<String, String> stream = streamBuilder.stream(config.getSubscribePattern(), Consumed.with(Serdes.String(), Serdes.String()));
        stream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
            @Override
            public Iterable<String> apply(String s) {
                return Arrays.stream(s.split(" ")).collect(Collectors.toList());
            }
        })
                .selectKey((k,v)->v)
                .peek((k,v)->{
                    //System.out.println(k+"\t"+v);
                })
                .groupByKey(Serialized.with(Serdes.String(),Serdes.String()))
                .count(Materialized.<String,Long, KeyValueStore<Bytes, byte[]>>as("wordcount"))
                .toStream()
                .foreach((k,v)->{
                    System.out.println(k+"\t"+v);
                });

        return stream;
    }

}
