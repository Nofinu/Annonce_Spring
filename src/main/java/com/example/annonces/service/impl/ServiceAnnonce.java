package com.example.annonces.service.impl;

import com.example.annonces.entity.Annonce;
import com.example.annonces.entity.Category;
import com.example.annonces.entity.Image;
import com.example.annonces.entity.User;
import com.example.annonces.repository.AnnonceRepository;
import com.example.annonces.repository.CategoryRepository;
import com.example.annonces.repository.ImageRepository;
import com.example.annonces.service.IServiceAnnonce;
import com.example.annonces.service.IServiceCategory;
import com.example.annonces.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceAnnonce implements IServiceAnnonce {
    @Autowired
    private AnnonceRepository _annonceRepository;

    @Autowired
    private IUploadService _uploadService;
    @Autowired
    private IServiceCategory _serviceCategory;
    @Autowired
    private ImageRepository _imageRepository;


    @Override
    public Annonce create(String title, String content, Double price, List<Integer> categoryId, List<MultipartFile> images, User user) throws Exception {
        if(title ==null || content == null || price == null || categoryId == null || user ==null){
            throw new Exception("tout les champs ne sont pas remplis");
        }
        Annonce annonce = new Annonce(title,content,price,user);
        return addCategoryAndImage(annonce,categoryId,images);
    }

    @Override
    public Annonce update(int id, String title, String content, Double price, List<Integer> categoryId,List<MultipartFile> images) throws Exception {
        if(id == 0 || title == null || content == null || price ==null || categoryId ==null){
            throw new Exception("tout les champs ne sont pas remplis");
        }
        Optional<Annonce> annonceFind = _annonceRepository.findById(id);
        if(annonceFind.isPresent()){
            Annonce annonce = annonceFind.get();
            annonce.setTitle(title);
            annonce.setContent(content);
            annonce.setPrice(price);
            annonce.setCategories(new ArrayList<>());

            return addCategoryAndImage(annonce,categoryId,images);
        }
        return null;
    }

    private Annonce addCategoryAndImage(Annonce annonce,List<Integer> categoryId,List<MultipartFile> images) throws IOException {
        for (Integer idCategory:categoryId) {
            annonce.addCategory(_serviceCategory.findById(idCategory));
        }
        _annonceRepository.save(annonce);
        if(!images.get(0).getOriginalFilename().equals("")){
            for (MultipartFile img: images){
                Image image =new Image();
                image.setUrl(_uploadService.store(img));
                image.setAnnonce(annonce);
                _imageRepository.save(image);
                annonce.addImage(image);
            }
        }
        return annonce;
    }

    @Override
    public boolean delete(int id) {
       Optional<Annonce> annonceFind = _annonceRepository.findById(id);
       if(annonceFind.isPresent()){
           _annonceRepository.delete(annonceFind.get());
           return true;
       }
       return false;
    }

    @Override
    public List<Annonce> findAll() {
        return (List<Annonce>) _annonceRepository.findAll();
    }

    @Override
    public Annonce findById(int id) {
        Optional<Annonce> annonce =  _annonceRepository.findById(id);
        return annonce.orElse(null);
    }

    @Override
    public List<Annonce> findByCategory(int idCategory) {
        Category category = _serviceCategory.findById(idCategory);
        return _annonceRepository.findByCategoriesContaining(category);
    }

    @Override
    public List<Annonce> findBytitle(String title) {
        return _annonceRepository.findByTitleContaining(title);
    }


}
