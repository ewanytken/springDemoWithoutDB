package com.newtry.demo.controller;

import com.newtry.demo.domain.User;
import com.newtry.demo.service.UserService;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-user")
    public ModelAndView createUserView() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user-creation");
        mav.addObject("user", new User());
        mav.addObject("allProfiles", getProfiles());
        logger.info("GetMapping method called.");
        return mav;
    }

    @PostMapping("/create-user")
    public ModelAndView createUser(User user, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
            logger.info("Validation errors while submitting form.");
            mav.setViewName("user-creation");
            mav.addObject("user", user);
            mav.addObject("allProfiles", getProfiles());
            return mav;
        }

        userService.addUser(user);
        mav.addObject("allUsers", userService.getAllUsersArticles());
        mav.setViewName("user-info");
        logger.info("Form submitted successfully.");
        return mav;
    }

    private List<String> getProfiles(){
        List<String> list = new ArrayList<>();
        list.add("Developer");
        list.add("Manager");
        list.add("Director");
        logger.info("getProfiles method used.");
        return list;
    }
}
