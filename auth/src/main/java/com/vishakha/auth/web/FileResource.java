package com.vishakha.auth.web;


import com.vishakha.auth.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
public class FileResource {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
//    form data ko accept krne ke liye annotation hai request param
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam MultipartFile file , @RequestParam String name)
    {
        System.out.println("fileContent = " + file);
        return fileService.uploadFile(file,name);
    }
    @GetMapping("/fetch-files")
    public List<Map<String,Object>> fetchFiles()
    {
        return fileService.fetchFiles();
    }

}
