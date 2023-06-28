package com.example.annonces.controller;

import com.example.annonces.entity.Annonce;
import com.example.annonces.service.impl.ServiceAnnonce;
import com.example.annonces.service.impl.ServiceCategory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import java.util.List;

@Controller
@RequestMapping("/annonce/search")
public class RechercheController {

    @Autowired
    private HttpSession _httpSession;

    @Autowired
    private ServiceAnnonce _serviceAnnonce;

    @Autowired
    private ServiceCategory _serviceCategory;


    @GetMapping("")
    public ModelAndView getSearch() {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isLogged()) {
            initSearchPage(modelAndView);
            List<Annonce> annonceList = _serviceAnnonce.findAll();
            modelAndView.addObject("annonces", annonceList);

        }
        return modelAndView;
    }

    @PostMapping("title")
    public ModelAndView postSearchTitle(@RequestParam("title") String title) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if(isLogged()){
            initSearchPage(modelAndView);
            modelAndView.addObject("annonces",_serviceAnnonce.findBytitle(title));
        }
        return modelAndView;
    }

    @GetMapping("/{categ}")
    public ModelAndView getSearchCategory (@PathVariable("categ")Integer idCategory){
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if(isLogged()){
            initSearchPage(modelAndView);
            modelAndView.addObject("annonces",_serviceAnnonce.findByCategory(idCategory));
        }
        return modelAndView;
    }

    private boolean isLogged() {
        if (_httpSession.getAttribute("isLogged") != null) {
            return (boolean) _httpSession.getAttribute("isLogged");
        }
        return false;
    }

    private void initSearchPage(ModelAndView mv) {
        mv.addObject("isLogged", _httpSession.getAttribute("isLogged"));
        mv.addObject("isAdmin", _httpSession.getAttribute("isAdmin"));

        mv.setViewName("ResearchPage");
        mv.addObject("userId", (Integer) _httpSession.getAttribute("userId"));
        List<Integer> favoris = (List<Integer>) _httpSession.getAttribute("favoris");
        mv.addObject("favoris", favoris);
        mv.addObject("categoryList", _serviceCategory.findAll());
        mv.addObject("url",-2);
    }
}
