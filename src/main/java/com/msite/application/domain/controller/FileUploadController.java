package com.msite.application.domain.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msite.application.domain.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileUploadController {

    private final FileService fileService;

    @PostMapping
    public List<String> uploadFile(@RequestPart List<MultipartFile> files) throws IOException, InterruptedException {
        return fileService.countWords(files);
    }
}
