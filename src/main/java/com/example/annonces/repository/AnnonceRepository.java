package com.example.annonces.repository;

import com.example.annonces.entity.Annonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce,Integer> {
}
