package com.carlfiller.kafkaoldspringboot.controllers;

import com.carlfiller.kafkaoldspringboot.data.ReadFromKafkaMessage;
import com.carlfiller.kafkaoldspringboot.models.Answer;
import com.carlfiller.kafkaoldspringboot.models.Question;
import com.carlfiller.kafkaoldspringboot.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("game")
public class GameController extends AbstractController {

    @RequestMapping(value = "game", method = RequestMethod.GET)
    public String gameFromKafkaDisplay(Model model, HttpServletRequest request) {
        User user = getUserFromSession(request.getSession());
        Question jeopardy = new ReadFromKafkaMessage().getQuestion();
        String question = jeopardy.getQuestion();
        String answer = jeopardy.getAnswer();
        String value = jeopardy.getValue();
        setAnswerInSession(request.getSession(), answer);
        model.addAttribute("title", "Kafka Jeopardy! Our contestant is:" + user.getUsername());
        model.addAttribute("question","Question is: " + question.substring(1,question.length()-1));
        model.addAttribute("value", "Value: " + value);
        model.addAttribute("score",user.getUsername() + "'s score is: " + user.getScore());
        model.addAttribute("answerObject", new Answer());
        return "game/game";
    }

    @RequestMapping(value = "game", method = RequestMethod.POST)
    public String processGame(@ModelAttribute Answer userAnswer, Model model, HttpServletRequest request) {
        User user = getUserFromSession(request.getSession());
        User existingUser = userDao.findByUsername(user.getUsername());
        if (userAnswer.getAnswer().equals(getAnswerFromSession(request.getSession()))) {
            int oldScore = existingUser.getScore();
            int newScore = oldScore + userAnswer.getValue();
            existingUser.setScore(newScore);
            userDao.save(existingUser);
            return "redirect:/game/game";
        }
        int oldScore = existingUser.getScore();
        int newScore = oldScore - userAnswer.getValue();
        existingUser.setScore(newScore);
        userDao.save(existingUser);
        return "redirect:/game/game";
    }

}
