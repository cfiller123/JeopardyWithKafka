package com.carlfiller.kafkaoldspringboot.controllers;

import com.carlfiller.kafkaoldspringboot.data.UserDao;
import com.carlfiller.kafkaoldspringboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractController {

    @Autowired
    protected UserDao userDao;

    public static final String userSessionKey = "user_id";

    public static final String answerSessionKey= "answer";

    protected User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findOne(userId);
    }

    protected void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey,user.getId());
    }

    protected String getAnswerFromSession(HttpSession session) {
        String answer = (String) session.getAttribute(answerSessionKey);
        return answer;
    }

    protected void setAnswerInSession(HttpSession session, String answer) {
        session.setAttribute(answerSessionKey,answer);
    }

    @ModelAttribute("user")
    public User getUserForModel(HttpServletRequest request) {
        return getUserFromSession(request.getSession());
    }
}
