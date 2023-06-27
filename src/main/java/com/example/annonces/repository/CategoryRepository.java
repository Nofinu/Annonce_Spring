package com.example.annonces.repository;

import com.example.annonces.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    public Category findByName (String name);
}
