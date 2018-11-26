package com.carlfiller.kafkaoldspringboot.kafka;

import com.carlfiller.kafkaoldspringboot.models.Question;
import com.google.gson.Gson;
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
        String groupId = "testGroupThree";

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer myConsumer = new KafkaConsumer(props);

        ArrayList<String> topics = new ArrayList<>();
        String topicName = "Jeopardy";
        topics.add(topicName);

        myConsumer.subscribe(topics);

        String message = "";

        try {
            outerloop:
            while (true) {
                ConsumerRecords<String, String> records = myConsumer.poll(1000);
                new Thread(new CreateKafkaMessage()).start();
                for (ConsumerRecord<String, String> record : records) {
                    message = record.value();
                    myConsumer.commitSync();
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
