package com.example.annonces.controller;

import com.example.annonces.entity.Category;
import com.example.annonces.service.impl.ServiceCategory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/annonce/category")
public class CategoryController {

    @Autowired
    private ServiceCategory _serviceCategory;

    @Autowired
    private HttpSession _httpSession;

    @GetMapping("")
    public ModelAndView getCategory() {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.addObject("categoryList", _serviceCategory.findAll());
            modelAndView.setViewName("CategoryMainPage");
        }
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getAddCategory() {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.setViewName("FormCategory");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditCategory(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.setViewName("redirect:/annonce/category");
            Category category = _serviceCategory.findById(id);
            if (category != null) {
                modelAndView.setViewName("FormCategory");
                modelAndView.addObject("category", category);
            }
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView getDeleteCategory(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.setViewName("redirect:/annonce/category");
            _serviceCategory.delete(id);
        }
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postAddCategory(@RequestParam("name") String name) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.setViewName("FormCategory");
            try {
                if (_serviceCategory.create(name) == null)
                    modelAndView.addObject("errorMessage", "category deja existante");
                else
                    modelAndView.setViewName("redirect:/annonce/category");

            } catch (Exception e) {
                modelAndView.addObject("errorMessage", e.getMessage());
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView postEditCategory(@PathVariable("id") Integer id, @RequestParam("name") String name) {
        ModelAndView modelAndView = new ModelAndView("redirect:/annonce");
        if (isAdmin()) {
            modelAndView.setViewName("FormCategory");
            try {
                if (_serviceCategory.update(id, name) == null)
                    modelAndView.addObject("errorMessage", "error durant la sauvegarde");
                else
                    modelAndView.setViewName("redirect:/annonce/category");

            } catch (Exception e) {
                modelAndView.addObject("errorMessage", e.getMessage());
            }
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
