package com.vishakha.auth.service;

import com.vishakha.auth.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;
    public void uploadFile(MultipartFile file , String name) {
        System.out.println("fileContent = " + file);
        Path uploads = Paths.get("uploads");
        if(!Files.exists(uploads)) {
            try {
                Files.createDirectory(uploads);
            } catch (IOException e) {
               throw new RuntimeException("Could not create directory");
            }
        }
        System.out.println(file.getOriginalFilename());
//        Clean the Path
        String cleandFileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Make a new Path Object
        Path newPath = uploads.resolve(cleandFileName);
        try
        {
            InputStream is = file.getInputStream();
            Files.copy(is,newPath, StandardCopyOption.REPLACE_EXISTING);
            fileRepository.uploads(file.getOriginalFilename(),name);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(newPath.toString()+" "+newPath);


    }
    public List<Map<String,Object>> fetchFiles()
    {
        return fileRepository.fetchFiles();
    }
}
