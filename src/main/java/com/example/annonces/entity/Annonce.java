package com.example.annonces.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String title;
    private String content;
    private double price;
    @Temporal(TemporalType.DATE)
    private Date dateAdd;
    private boolean status;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Image> images = new ArrayList<>();
    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category){
        this.categories.add(category);
    }
    public void addImage(Image image){
        this.images.add(image);
    }

    public Annonce() {
    }

    public Annonce(String title, String content, double price, User user) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.user = user;
        this.status = true;
        this.dateAdd = new Date(System.currentTimeMillis());
    }


}
