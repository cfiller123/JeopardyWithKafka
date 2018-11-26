package com.carlfiller.kafkaoldspringboot.kafka;

import com.carlfiller.kafkaoldspringboot.data.ReadFromJsonFile;
import com.carlfiller.kafkaoldspringboot.models.Question;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class CreateKafkaMessage implements Runnable {

    public void run() {
        Properties props = new Properties();
        String address = "localhost:9092";
        String valueSerializer = "org.apache.kafka.common.serialization.StringSerializer";
        String keySerializer = "org.apache.kafka.common.serialization.StringSerializer";
        String topics = "Jeopardy";
        props.put("bootstrap.servers", address);
        props.put("key.serializer", valueSerializer);
        props.put("value.serializer",keySerializer);

        KafkaProducer<String, String> myProducer = new KafkaProducer<>(props);

        try {
            int counter = 0;
            while (counter < 1) {
                Question message = ReadFromJsonFile.getQuestion();
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(message);
                myProducer.send(new ProducerRecord<>(topics,jsonStr));
                counter ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myProducer.close();
        }
    }
}
