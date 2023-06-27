package com.example.annonces.service;

import com.example.annonces.entity.Category;

import java.util.List;

public interface IServiceCategory {
    public Category create (String name) throws Exception;

    public Category update (int id,String name) throws Exception;

    public boolean delete (int id);

    public List<Category> findAll();
    public Category findById(int id);
    public Category findByName (String name);
}
