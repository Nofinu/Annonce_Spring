package com.example.annonces.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    String store(MultipartFile file) throws IOException;
}
