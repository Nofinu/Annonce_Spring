package com.example.annonces.repository;

import com.example.annonces.entity.Annonce;
import com.example.annonces.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce,Integer> {
    public List<Annonce> findByCategoriesContaining (Category category);
    public List<Annonce> findByTitleContaining (String title);
}
