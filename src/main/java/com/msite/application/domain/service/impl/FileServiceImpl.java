package com.msite.application.domain.service.impl;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.apachecommons.CommonsLog;

import com.msite.application.domain.model.ProcessFile;
import com.msite.application.domain.service.*;

@Service
@CommonsLog
public class FileServiceImpl implements FileService {

    public List<String> countWords(List<MultipartFile> files) throws IOException {
        TreeMap<String, Integer> combineCount = new TreeMap<String, Integer>();

        List<ProcessFile> threads = new ArrayList<>();

        for (MultipartFile file : files) {
            threads.add(new ProcessFile(convertMultiPartFileToFile(file)));
        }

        for (ProcessFile t : threads) {
            try {
                t.start();
                t.join();
                combineCount.putAll(t.getWordMap());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            return generateWordFiles(combineCount);
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    private List<String> generateWordFiles(TreeMap<String, Integer> wordMap) throws IOException {
        FileWriter writerAG = new FileWriter("A-G.txt");
        FileWriter writerHN = new FileWriter("H-N.txt");
        FileWriter writerOU = new FileWriter("O-U.txt");
        FileWriter writerVZ = new FileWriter("V-Z.txt");
        PrintWriter printWriterAG = new PrintWriter(writerAG);
        PrintWriter printWriterHN = new PrintWriter(writerHN);
        PrintWriter printWriterOU = new PrintWriter(writerOU);
        PrintWriter printWriterVZ = new PrintWriter(writerVZ);

        List<String> wordList = new ArrayList<>();

        for (Map.Entry<String, Integer> word : wordMap.entrySet()) {
            if (word.getKey().charAt(0) >= 'A' && word.getKey().charAt(0) <= 'G') {
                printWriterAG.println(word.getKey() + " is repeated " + word.getValue());
            }
            if (word.getKey().charAt(0) >= 'H' && word.getKey().charAt(0) <= 'N') {
                printWriterHN.println(word.getKey() + " is repeated " + word.getValue());
            }
            if (word.getKey().charAt(0) >= 'O' && word.getKey().charAt(0) <= 'U') {
                printWriterOU.println(word.getKey() + " is repeated " + word.getValue());
            }
            if (word.getKey().charAt(0) >= 'V' && word.getKey().charAt(0) <= 'Z') {
                printWriterVZ.println(word.getKey() + " is repeated " + word.getValue());
            }
            wordList.add(word.getKey() + " is repeated " + word.getValue());
        }

        writerAG.close();
        writerHN.close();
        writerOU.close();
        writerVZ.close();

        return wordList;
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) throws IOException {
        File targetFile = new File(multipartFile.getOriginalFilename());

        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            outputStream.write(multipartFile.getBytes());
        }

        return targetFile;
    }
}
