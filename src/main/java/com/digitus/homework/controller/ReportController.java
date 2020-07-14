package com.digitus.homework.controller;

import com.digitus.homework.model.LoggedStatus;
import com.digitus.homework.model.UserStats;
import com.digitus.homework.service.TokenService;
import com.digitus.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value={"/report"}, method = RequestMethod.GET)
    public ModelAndView report(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/report/home");
        return modelAndView;
    }

    @RequestMapping(value={"/report/users"}, method = RequestMethod.GET)
    public ModelAndView userReport(){
        UserStats stats = new UserStats();
        stats.setTotalUsers(userService.getNumberOfUsers());
        stats.setLoggedInUsers(userService.getNumberOfUsersByStatus(LoggedStatus.LOGGED_IN.value));
        stats.setLoggedOutUsers(userService.getNumberOfUsersByStatus(LoggedStatus.LOGGED_OUT.value));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userstats", stats);
        modelAndView.setViewName("/report/users");
        return modelAndView;
    }

    @RequestMapping(value={"/report/newuser"}, method = RequestMethod.GET)
    public ModelAndView newUsers(){
        Integer numberNewUsersIn24Hours = userService.countNewUsersIn24Hours();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("numberNewUsersIn24Hours", numberNewUsersIn24Hours);
        modelAndView.setViewName("/report/newusers");
        return modelAndView;
    }

    @RequestMapping(value={"/report/expired"}, method = RequestMethod.GET)
    public ModelAndView expired(){
        Integer numberOfExpiredConfirmations = tokenService.countExpiredConfirmations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("numberOfExpiredConfirmations", numberOfExpiredConfirmations);
        modelAndView.setViewName("/report/expired");
        return modelAndView;
    }
}
