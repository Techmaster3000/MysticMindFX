package com.example.mysticmindfx;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class HistoryHandler {
    private static @NotNull ArrayList<String> getStrings(VBox ChatHistory) {
        ArrayList<String> HistoryList = new ArrayList<>();

        for (int i = 0; i < ChatHistory.getChildren().size(); i++) {
            HBox chatMessage = (HBox) ChatHistory.getChildren().get(i);
            Text messageText;
            try {
                messageText = (Text) chatMessage.getChildren().get(1);
            } catch (Exception e) {
                messageText = (Text) chatMessage.getChildren().get(0);
            }

            String message = messageText.getText();
            //ignore the text wrapping
            message = message.replace("\n", "\\n");
            if (chatMessage.getStyleClass().contains("message")) {
                HistoryList.add("User: " + message);
            } else {
                HistoryList.add("AI: " + message);
            }
        }
        return HistoryList;
    }

    public static void userRename(String oldName, String newName) {
        //replace the first line in every file that contains the oldName with the newName
        File folder = new File("src/chatHistory/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            try {
                BufferedReader reader =     new BufferedReader(new FileReader(file));
                String line;
                //check if the first line is the user's name
                line = reader.readLine();
                if (line == null) {
                    return;
                }
                if (line.equals(oldName)) {
                    //replace the first line with the newName
                    FileWriter writer = new FileWriter(file);
                    writer.write(newName + "\n");
                    while ((line = reader.readLine()) != null) {
                        writer.write(line + "\n");
                    }
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<String> retrieveHistory(File file, String chatName) {
        ArrayList<String> chatHistory = new ArrayList<>();
        try {
            String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
            if (!fileNameWithoutExtension.equals(chatName)) {
                System.out.println(fileNameWithoutExtension + " " + chatName + " Dit allemaal ");
                return chatHistory;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    chatHistory.add(line);
                }
            }
        } catch (Exception e) {
            // Handle exception...
        }
        return chatHistory;
    }

    public void saveHistory(String chatName, VBox ChatHistory, String user) {
        ArrayList<String> HistoryList = getStrings(ChatHistory);
        try {
            File file = new File("src/chatHistory/" + chatName + ".txt");
            //clear the file
            if (!file.exists() && !file.createNewFile()) {
                System.out.println("Historyfile could not be created");

            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(user + "\n");
                writer.write(chatName + "\n");
                for (String line : HistoryList) {
                    writer.write(line + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
