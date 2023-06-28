package com.example.annonces.controller;

import com.example.annonces.entity.Annonce;
import com.example.annonces.service.impl.ServiceAnnonce;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/annonce/favoris")
public class FavorisController {

    @Autowired
    private ServiceAnnonce _serviceAnnonce;

    @Autowired
    private HttpSession _httpSession;

    @GetMapping("")
    public ModelAndView getFavoris(){
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if(isLogged()){
            modelAndView.setViewName("FavorisMainPage");
            List<Integer> favoris = (List<Integer>) _httpSession.getAttribute("favoris");
            List<Annonce> annonces = new ArrayList<>();
            for (Integer id:favoris) {
                annonces.add(_serviceAnnonce.findById(id));
            }
            modelAndView.addObject("annonces",annonces);
            modelAndView.addObject("isLogged",_httpSession.getAttribute("isLogged"));
            modelAndView.addObject("isAdmin",_httpSession.getAttribute("isAdmin"));
            modelAndView.addObject("favoris",favoris);
            modelAndView.addObject("url","/annonce/favoris");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/{url}")
    public ModelAndView getAddFavoris(@PathVariable("id") Integer id, @PathVariable("url")Integer url) {
        ModelAndView modelAndView= new ModelAndView("redirect:/annonce");
        if(isLogged()){
            List<Integer> favoris = (List<Integer>) _httpSession.getAttribute("favoris");
            if(favoris.contains(id)){
                favoris.remove(id);
            }else{
                favoris.add(id);
            }
            _httpSession.setAttribute("favoris",favoris);
            switch (url){
                case 0:
                    modelAndView.setViewName("redirect:/annonce");
                    break;
                case -1:
                    modelAndView.setViewName("redirect:/annonce/favoris");
                    break;
                case -2:
                    modelAndView.setViewName("redirect:/annonce/search");
                    break;
                default:
                    modelAndView.setViewName("redirect:/annonce/"+url);
                    break;
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
