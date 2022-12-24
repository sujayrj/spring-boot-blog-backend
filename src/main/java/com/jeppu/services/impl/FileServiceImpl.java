package com.jeppu.services.impl;

import com.jeppu.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        //generate randomUUI
        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        String fullPath = path+File.separator+randomFileName;

        //check if dir exists
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }

        //copy file
        Files.copy(multipartFile.getInputStream(), Paths.get(fullPath));
        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
