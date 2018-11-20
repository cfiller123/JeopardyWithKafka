package com.carlfiller.kafkaoldspringboot;

import com.carlfiller.kafkaoldspringboot.controllers.AbstractController;
import com.carlfiller.kafkaoldspringboot.data.UserDao;
import com.carlfiller.kafkaoldspringboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        List<String> nonAuthPages = Arrays.asList("/user/login","/user/register");
        if ( !nonAuthPages.contains(request.getRequestURI())) {
            Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userSessionKey);
            if (userId != null) {
                User user = userDao.findOne(userId);
                if (user != null) {
                    return true;
                }
            }
            response.sendRedirect("/user/login");
            return false;
        }
        return true;
    }
}
