package com.example.mysticmindfx;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

public class HistoryHandler {
    public ArrayList<String> retrieveHistory(File file, String user) {
        ArrayList<String> chatHistory = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            //check if the first line is the user's name
            line = reader.readLine();
            if (line == null) {
                return chatHistory;
            }
            if (!line.equals(user)) {
                System.out.println(line + " " + user);
                return chatHistory;
            }
            while ((line = reader.readLine()) != null) {
                chatHistory.add(line);
            }
            reader.close();
        } catch (Exception e) {
            //create a new file if it doesn't exist
            try {
                file.createNewFile();
                //add an empty entry to the chatHistory
                chatHistory.add("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return chatHistory;
    }
    public void saveHistory(String chatName, VBox ChatHistory, String user) {
        ArrayList<String> HistoryList = new ArrayList<>();

        for (int i = 0; i < ChatHistory.getChildren().size(); i++) {
            HBox chatMessage = (HBox) ChatHistory.getChildren().get(i);
            Text messageText;
            try {
                messageText = (Text) chatMessage.getChildren().get(1);
            }
            catch (Exception e) {
                messageText = (Text) chatMessage.getChildren().get(0);
            }

            String message = messageText.getText();
            if (chatMessage.getStyleClass().contains("message")) {
                HistoryList.add("User: " + message);
            } else {
                HistoryList.add("AI: " + message);
            }
        }
        try {
            File file = new File("src/chatHistory/" + chatName + ".txt");
            //clear the file
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(user + "\n");
            writer.write(chatName + "\n");
            for (String line : HistoryList) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
