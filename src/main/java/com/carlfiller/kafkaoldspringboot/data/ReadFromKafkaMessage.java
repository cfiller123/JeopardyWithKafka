package com.carlfiller.kafkaoldspringboot.data;

import com.carlfiller.kafkaoldspringboot.kafka.GetKafkaMessage;
import com.carlfiller.kafkaoldspringboot.models.Question;
import com.google.gson.Gson;

public class ReadFromKafkaMessage {

    public Question getQuestion() {
        String json = GetKafkaMessage.getJson();

        try {
            Gson gson = new Gson();
            Question question = gson.fromJson(json,Question.class);
            return question;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Question();
    }
}
