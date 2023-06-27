package com.example.annonces.service.impl;

import com.example.annonces.entity.User;
import com.example.annonces.repository.UserRepository;
import com.example.annonces.service.IServiceUser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser implements IServiceUser {
    @Autowired
    private UserRepository _userRepository;
    @Override
    public boolean register(String username, String password) {
        if(_userRepository.findByUsername(username) == null){
            User user = new User();
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt(10)));
            _userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        if(_userRepository.findByUsername(username) != null){
            User userFind = _userRepository.findByUsername(username);
            if(userFind.isActive()){
                return BCrypt.checkpw(password, userFind.getPassword());
            }
        }
        return false;
    }

    @Override
    public boolean updateStatus(int id) {
        Optional<User> userFind = _userRepository.findById(id);
        if(userFind.isPresent()){
            User user = userFind.get();
            user.setActive();
            _userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin(String username) {
        User user = _userRepository.findByUsername(username);
        return user.isAdmin();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = _userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) _userRepository.findAll();
    }

    public User findByUsername(String username){
        return _userRepository.findByUsername(username);
    }

}
