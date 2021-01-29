package com.jdkj.stream;

import com.jdkj.config.AtalsBridgeCenterConfigurations;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class MetaDataMessageRouter {
    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamBuilder, AtalsBridgeCenterConfigurations config) {

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
