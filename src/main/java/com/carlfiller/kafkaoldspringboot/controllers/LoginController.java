package com.carlfiller.kafkaoldspringboot.controllers;

import com.carlfiller.kafkaoldspringboot.models.LoginForm;
import com.carlfiller.kafkaoldspringboot.models.RegisterForm;
import com.carlfiller.kafkaoldspringboot.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class LoginController extends AbstractController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Welcome to Kafka Jeopardy");
        model.addAttribute("loginForm", new LoginForm());
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(@ModelAttribute @Valid LoginForm form, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "user/login";
        }
        User theUser = userDao.findByUsername(form.getUsername());
        String password = form.getPassword();

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "This user does not exist");
            return "user/login";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            return "user/login";
        }

        setUserInSession(request.getSession(),theUser);
        return "redirect:/game/game";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("title", "Register for Kafka Jeopardy");
        model.addAttribute("registerForm", new RegisterForm());
        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute @Valid RegisterForm form, Model model, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "user/login";
        }

        User existingUser = userDao.findByUsername(form.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that name already exists");
            model.addAttribute("title", "Register for Kafka Jeopardy");
            return "user/register";
        }

        User newUser = new User(form.getUsername(), form.getPassword());
        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);
        return "redirect:/game/game";
    }

    @RequestMapping(value = "logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/user/login";
    }
}

