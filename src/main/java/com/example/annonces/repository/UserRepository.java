package com.example.annonces.repository;

import com.example.annonces.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    public User findByUsername(String username);
}
