package com.example.annonces.service;


import com.example.annonces.entity.Annonce;
import com.example.annonces.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceAnnonce {
    public Annonce create (String title, String content, Double price, List<Integer> categoryId, List<MultipartFile> images, User user) throws Exception;
    public Annonce update (int id,String title, String content, Double price, List<Integer> categoryId,List<MultipartFile> images) throws Exception;
    public boolean delete (int id);
    public List<Annonce> findAll();
    public Annonce findById(int id);
}
