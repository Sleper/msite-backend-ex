package com.msite.application.domain.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    List<String> countWords(List<MultipartFile> files) throws IOException;
}
