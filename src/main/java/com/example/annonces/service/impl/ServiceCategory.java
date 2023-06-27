package com.example.annonces.service.impl;

import com.example.annonces.entity.Category;
import com.example.annonces.repository.CategoryRepository;
import com.example.annonces.service.IServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategory implements IServiceCategory {
    @Autowired
    private CategoryRepository _categoryRepository;

    @Override
    public Category create(String name) throws Exception {
        if(name == null){
            throw new Exception("champs non remplis");
        }
        if(_categoryRepository.findByName(name) == null){
            Category category =new Category(name);
            return _categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public Category update(int id,String name) throws Exception {
        if(name == null){
            throw new Exception("champs non remplis");
        }
        Optional<Category> categoryFind = _categoryRepository.findById(id);
        if(categoryFind.isPresent()){
            Category category = categoryFind.get();
            category.setName(name);
            return _categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        Optional<Category> categoryFind = _categoryRepository.findById(id);
        if(categoryFind.isPresent()){
            _categoryRepository.delete(categoryFind.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) _categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> category = _categoryRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public Category findByName(String name) {
        return _categoryRepository.findByName(name);
    }
}
