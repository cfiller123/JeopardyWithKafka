package com.carlfiller.kafkaoldspringboot.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class CreateKafkaMessage {

    public void createJson() {
        Properties props = new Properties();
        String address = "18.223.107.7:9092";
        String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
        String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
        String topics = "test";
        props.put("bootstrap-servers", address);
        props.put("key.serializer", valueSerializer);
        props.put("value.serializer",keySerializer);

        KafkaProducer<String, String> myProducer = new KafkaProducer<String, String>(props);

//        Need to produce new question here
    }
}
