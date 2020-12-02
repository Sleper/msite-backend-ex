package com.msite.application.domain.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;


public class ProcessFile extends Thread {

    private final File file;
    private final TreeMap<String, Integer> wordMap = new TreeMap<>();

    public ProcessFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try {
            countWordsInFile(wordMap, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void countWordsInFile(TreeMap<String, Integer> wordMap, File file) throws IOException {
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()) {
            while (sc.hasNext()) {
                String next = sc.next();
                wordMap.merge(next.toUpperCase(), 1, Integer::sum);
            }
        }
        sc.close();
    }

    public TreeMap<String, Integer> getWordMap() {
        return wordMap;
    }
}
