package com.example.annonces.controller;

import com.example.annonces.service.impl.ServiceUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/annonce/user")
public class UserController {

    @Autowired
    private HttpSession _httpSession;

    @Autowired
    private ServiceUser _serviceUser;

    @GetMapping("")
    public ModelAndView getUser(){
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if(isAdmin()){
            modelAndView.addObject("userList",_serviceUser.findAll());
            modelAndView.setViewName("UserMainPage");
        }
        return modelAndView;
    }

    @GetMapping("/setstatut/{id}")
    public ModelAndView getSetStatut (@PathVariable("id")Integer id){
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if(isAdmin()){
            modelAndView.setViewName("redirect:/annonce/user");
            _serviceUser.updateStatus(id);
        }
        return modelAndView;
    }

    private boolean isAdmin() {
        if (_httpSession.getAttribute("isAdmin") != null) {
            return (boolean) _httpSession.getAttribute("isAdmin");
        }
        return false;
    }
}
