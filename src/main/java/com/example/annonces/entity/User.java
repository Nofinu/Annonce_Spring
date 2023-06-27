package com.example.annonces.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin = false;
    private boolean isActive = true;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Annonce> annonces;


    public void setActive (){
        this.isActive = !isActive;
    }
}
