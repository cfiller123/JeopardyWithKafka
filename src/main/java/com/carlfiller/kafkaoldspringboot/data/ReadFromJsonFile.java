package com.carlfiller.kafkaoldspringboot.data;

import com.carlfiller.kafkaoldspringboot.models.Question;
import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFromJsonFile {

    public static Question getQuestion() {
        String location = "C:\\Users\\CDF1104\\IdeaProjects\\kafka-old-springboot\\src\\main\\resources\\static\\JEOPARDY_QUESTIONS1.json";
        String json;

        try {
            Gson gson = new Gson();
            json = new String (Files.readAllBytes(Paths.get(location)));
            Question[] questions = gson.fromJson(json, Question[].class);
            int randomQuestion = 1 + (int)(Math.random()*((500-1) + 1));
            Question question = questions[randomQuestion];
            return question;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Question();
    }

    public static String getJsonString() {
        String location = "C:\\Users\\CDF1104\\IdeaProjects\\kafka-old-springboot\\src\\main\\resources\\static\\JEOPARDY_QUESTIONS1.json";
        String json;

        try {
            Gson gson = new Gson();
            json = new String (Files.readAllBytes(Paths.get(location)));
            String[] questions = gson.fromJson(json,String[].class);
            int randomQuestion = 1 + (int)(Math.random()*((500-1) + 1));
            String question = questions[randomQuestion];
            return question;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
