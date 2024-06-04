package com.example.mysticmindfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class HistoryHandler {
    public ArrayList<String> retrieveHistory(File file) {
        ArrayList<String> chatHistory = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                chatHistory.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatHistory;
    }
}
