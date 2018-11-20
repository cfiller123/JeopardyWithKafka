package com.carlfiller.kafkaoldspringboot.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Properties;

public class GetKafkaMessage {

    public static String getJson() {
        String address = "localhost:9092";
        String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
        String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
        String groupId = "testGroupTwo";

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer myConsumer = new KafkaConsumer(props);

        ArrayList<String> topics = new ArrayList<>();
        String topicName = "Jeopardy";
        topics.add(topicName);

        myConsumer.subscribe(topics);

        String message = "";

        try {
            outerloop:
            while (true) {
                ConsumerRecords<String, String> records = myConsumer.poll(10);
                for (ConsumerRecord<String, String> record : records) {
                    message = record.value();
                    System.out.println("pre sync");
                    myConsumer.commitSync();
                    System.out.println("post sync");
                    break outerloop;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myConsumer.close();
        }

        return message;
    }
}
