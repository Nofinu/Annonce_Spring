package com.example.annonces.service;

import com.example.annonces.entity.User;

import java.util.List;

public interface IServiceUser {
    public boolean register(String username,String password);
    public boolean login(String username,String password);
    public boolean updateStatus(int id);
    public boolean updateAdmin(int id);
    public boolean isAdmin(String username);
    public User findById(int id);
    public List<User> findAll();


}
