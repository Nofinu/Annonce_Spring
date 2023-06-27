package com.example.annonces.controller;

import com.example.annonces.entity.User;
import com.example.annonces.service.impl.ServiceUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/annonce/log")
public class LoginController {

    @Autowired
    private HttpSession _httpSession;

    @Autowired
    private ServiceUser _serviceUser;


    @GetMapping("/{type}")
    public ModelAndView getLogPage(@PathVariable("type") String type){
        ModelAndView modelAndView = new ModelAndView();
        if(_httpSession.getAttribute("isLogged") == null || _httpSession.getAttribute("isLogged").equals(false)){
            modelAndView.setViewName("LogForm");
            modelAndView.addObject("type",type);
        }
        else
            modelAndView.setViewName("redirect:/annonce");

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView getLogOut(){
        ModelAndView modelAndView =new ModelAndView("redirect:/annonce");
        _httpSession.setAttribute("isLogged",false);
        _httpSession.setAttribute("isAdmin",false);
        _httpSession.setAttribute("userId",0);
        _httpSession.setAttribute("favoris",new ArrayList<>());
        return modelAndView;
    }


    @PostMapping("/{type}")
    public ModelAndView postLogPage (@PathVariable("type")String type, @RequestParam("username") String username,@RequestParam("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("LogForm");
        if(type.equals("register")){
            if(_serviceUser.register(username,password)){
                _httpSession.setAttribute("isLogged",true);
                _httpSession.setAttribute("isAdmin",false);
                User user = _serviceUser.findByUsername(username);
                _httpSession.setAttribute("userId",user.getId());
                List<Integer> favoris = new ArrayList<>();
                _httpSession.setAttribute("favoris",favoris);
                modelAndView.setViewName("redirect:/annonce");
            }else
                modelAndView.addObject("errorMessage","erreure durant la creation de l'utilisateur");
        }else{
            if(_serviceUser.login(username,password)){
                _httpSession.setAttribute("isLogged",true);
                User user = _serviceUser.findByUsername(username);
                _httpSession.setAttribute("userId",user.getId());
                List<Integer> favoris = new ArrayList<>();
                _httpSession.setAttribute("favoris",favoris);

                if(_serviceUser.isAdmin(username))
                    _httpSession.setAttribute("isAdmin",true);
                else
                    _httpSession.setAttribute("isAdmin",false);

                modelAndView.setViewName("redirect:/annonce");
            }else
                modelAndView.addObject("errorMessage","username ou password incorrect");
        }
        return modelAndView;
    }
}
