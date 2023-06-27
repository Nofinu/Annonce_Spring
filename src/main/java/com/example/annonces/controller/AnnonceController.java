package com.example.annonces.controller;

import com.example.annonces.entity.Annonce;
import com.example.annonces.entity.User;
import com.example.annonces.service.impl.ServiceAnnonce;
import com.example.annonces.service.impl.ServiceCategory;
import com.example.annonces.service.impl.ServiceUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/annonce")
public class AnnonceController {

    @Autowired
    private HttpSession _httpSession;

    @Autowired
    private ServiceCategory _serviceCategory;

    @Autowired
    private ServiceAnnonce _serviceAnnonce;

    @Autowired
    private ServiceUser _serviceUser;

    @GetMapping("")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("HomePage");

        if (_httpSession.getAttribute("isLogged") == null)
            modelAndView.addObject("isLogged", false);
        else
            modelAndView.addObject("isLogged", _httpSession.getAttribute("isLogged"));

        if (_httpSession.getAttribute("isAdmin") == null)
            modelAndView.addObject("isAdmin", false);
        else
            modelAndView.addObject("isAdmin", _httpSession.getAttribute("isAdmin"));

        List<Annonce> annonces = _serviceAnnonce.findAll();
        modelAndView.addObject("annonces", annonces);

        modelAndView.addObject("userId", (Integer) _httpSession.getAttribute("userId"));

        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getFormAddAnnonce() {
        ModelAndView modelAndView = new ModelAndView("HomePage");
        if (isLogged()) {
            modelAndView.setViewName("FormAnnonce");
            modelAndView.addObject("categoryList", _serviceCategory.findAll());
        }
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postAddAnnonce(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("price") double price, @RequestParam("category") List<Integer> category, @RequestParam("image") List<MultipartFile> images) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isLogged()) {
            modelAndView.setViewName("redirect:/annonce/add");
            int userId = (Integer) _httpSession.getAttribute("userId");
            try {
                User user = _serviceUser.findById(userId);
                if (user != null) {
                    _serviceAnnonce.create(title, content, price, category, images, user);
                    modelAndView.setViewName("redirect:/annonce");
                }
            } catch (Exception e) {
                modelAndView.addObject("errorMessage", e.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditAnnonce(@PathVariable("id") Integer id) {
        ModelAndView modelAndView =new ModelAndView("redirect:/annonce");
        Annonce annonce = _serviceAnnonce.findById(id);
        if(isLogged() && annonce.getUser().getId() == (Integer)_httpSession.getAttribute("userId")){
            modelAndView.addObject("categoryList", _serviceCategory.findAll());
            modelAndView.setViewName("FormAnnonce");
            modelAndView.addObject("annonce",annonce);
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView postEditAnnonce(@PathVariable("id")Integer id,@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("price") double price, @RequestParam("category") List<Integer> category){
        ModelAndView modelAndView =new ModelAndView("redirect:/annonce");
        Annonce annonce = _serviceAnnonce.findById(id);
        if(isLogged() && annonce.getUser().getId() == (Integer)_httpSession.getAttribute("userId")){
            modelAndView.setViewName("redirect:/annonce/add");
            try{
                _serviceAnnonce.update(id,title,content,price,category);
                modelAndView.setViewName("redirect:/annonce");
            }catch (Exception e){
                modelAndView.addObject("errorMessage",e.getMessage());
            }
        }
        return modelAndView;
    }


    private boolean isLogged() {
        if (_httpSession.getAttribute("isLogged") != null) {
            return (boolean) _httpSession.getAttribute("isLogged");
        }
        return false;
    }
}
