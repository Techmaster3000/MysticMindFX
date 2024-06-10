package com.example.mysticmindfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements IController {
    private static MainController instance = null;
    @FXML
    private ScrollPane sidebarScroll;
    @FXML
    private VBox ChatTabBox;
    @FXML
    private ToolBar ToolBar;
    @FXML
    private TextField ChatField;
    @FXML
    private VBox ChatHistory;
    @FXML
    private ScrollPane ChatScroll;
    @FXML
    private Text ChatTitle;
    @FXML
    private Button RenameButton;

    private String user = null;
    private String selectedChat = null;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        Button settingsButton = createSettingsButton();
        Button plusButton = createAddButton();
        ToolBar.getItems().add(settingsButton);
        ToolBar.getItems().add(plusButton);
        loadHistory();
        ChatHistory.setSpacing(10);
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void loadHistory() {
        File historyFolder = new File("src/chatHistory");

        HistoryHandler historyHandler = new HistoryHandler();
        ChatTabBox.getChildren().clear();
        for (File file : historyFolder.listFiles()) {
            ArrayList<String> chatHistory = historyHandler.retrieveHistory(file, user);
            Button chat;
            try {
                chat = new Button(chatHistory.get(0));
                chat.setOnAction(event -> loadChat(chat.getText()));
                chat.getStyleClass().add("MenuItem");
                ChatTabBox.getChildren().add(chat);
            } catch (Exception e) {
                System.out.println("Empty file found");
            }

            if (!chatHistory.isEmpty()) {
                chatHistory.clear();
            }
        }
    }

    @FXML
    protected void showRenamePopUp() throws IOException {
        if (selectedChat == null) {
            return;
        }
        String renamePopUpFile = "RenamePopUp.fxml";
        if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
            renamePopUpFile = renamePopUpFile.replace(".fxml", "-NL.fxml");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(renamePopUpFile));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
            stage.setTitle("Chat hernoemen");
        } else {
            stage.setTitle("Rename Chat");
        }
        stage.show();
    }

    @FXML
    protected void onDeleteChat() {
        if (selectedChat == null) {
            return;
        }
        int toDeleteIndex = -1;
        for (int i = 0; i < ChatTabBox.getChildren().size(); i++) {
            Button chat = (Button) ChatTabBox.getChildren().get(i);
            if (chat.getText().equals(selectedChat)) {
                toDeleteIndex = i;
                break;
            }
        }

        Alert alert;
        if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Verwijder " + selectedChat + " ?", ButtonType.YES, ButtonType.NO);
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selectedChat + " ?", ButtonType.YES, ButtonType.NO);
        }
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Path sourcePath = Paths.get("src/chatHistory/" + selectedChat + ".txt");
            try {
                Files.delete(sourcePath);
            } catch (Exception e) {
                System.out.println("Failed to delete file: " + e.getMessage());
            }
            selectedChat = null;
            ChatHistory.getChildren().clear();
            ChatTabBox.getChildren().remove(toDeleteIndex);
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                ChatTitle.setText("Chat");
            } else {
                ChatTitle.setText("Chat");
            }
        }
    }

    public void renameChat(String newName) {
        for (int i = 0; i < ChatTabBox.getChildren().size(); i++) {
            Button chat = (Button) ChatTabBox.getChildren().get(i);
            if (chat.getText().equals(selectedChat)) {
                chat.setText(newName);
                HistoryHandler historyHandler = new HistoryHandler();
                Path sourcePath = Paths.get("src/chatHistory/" + selectedChat + ".txt");
                Path targetPath = Paths.get("src/chatHistory/" + newName + ".txt");
                try {
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    System.out.println("Failed to rename file: " + e.getMessage());
                }
                selectedChat = newName;
                ChatTitle.setText(newName);
                historyHandler.saveHistory(selectedChat, ChatHistory, user);
                break;
            }
        }
    }

    @FXML
    protected void addChat() {
        Button newChat = new Button("Chat " + (ChatTabBox.getChildren().size() + 1));
        newChat.getStyleClass().add("MenuItem");
        newChat.setStyle("-fx-text-fill: white;");
        newChat.setOnAction(event -> loadChat(newChat.getText()));
        ChatTabBox.getChildren().add(newChat);
        try {
            File file = new File("src/chatHistory/" + newChat.getText() + ".txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(user + "\n");
            writer.write(newChat.getText() + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Failed to create file: " + e.getMessage());
        }
        loadChat(newChat.getText());
    }

    private void loadChat(String chatName) {
        ChatHistory.getChildren().clear();
        Button chat = null;

        for (int i = 0; i < ChatTabBox.getChildren().size(); i++) {
            chat = (Button) ChatTabBox.getChildren().get(i);
            chat.getStyleClass().remove("selectedChat");
        }

        for (int i = 0; i < ChatTabBox.getChildren().size(); i++) {
            chat = (Button) ChatTabBox.getChildren().get(i);
            Boolean isSelected = chat.getText().equals(chatName);
            if (isSelected) {
                chat.getStyleClass().add("selectedChat");
            }
        }

        HistoryHandler historyHandler = new HistoryHandler();
        File folder = new File("src/chatHistory");
        ArrayList<String> chatHistory = null;
        for (File file : folder.listFiles()) {
            ArrayList<String> tempChatHistory = historyHandler.retrieveHistory(file, user);
            try {
                if (tempChatHistory.get(0).equals(chatName)) {
                    chatHistory = tempChatHistory;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Empty file found");
            }
        }
        Boolean fromAI = null;
        if (chatHistory != null && !chatHistory.isEmpty()) {
            chatHistory.remove(0);
            for (String line : chatHistory) {
                HBox chatMessage = new HBox();
                if (line.startsWith("User: ")) {
                    chatMessage.setAlignment(Pos.CENTER_RIGHT);
                    chatMessage.getStyleClass().add("message");
                    line = line.substring(6);
                    fromAI = false;
                } else {
                    chatMessage.setAlignment(Pos.CENTER_LEFT);
                    chatMessage.getStyleClass().add("response");
                    line = line.substring(4);
                    fromAI = true;
                }
                Text messageText = new Text(line);
                messageText.setStyle("-fx-fill: white;");
                if (fromAI) {
                    ImageView AILogo = new ImageView();
                    AILogo.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/logo.png").toString()));
                    AILogo.setFitHeight(39);
                    AILogo.setFitWidth(39);
                    AILogo.getStyleClass().add("userIcon");
                    chatMessage.getChildren().add(AILogo);
                    chatMessage.setSpacing(10);
                    chatMessage.getChildren().add(messageText);
                } else {
                    ImageView user = new ImageView();
                    user.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/profile-user.png").toString()));
                    user.setFitHeight(39);
                    user.setFitWidth(39);
                    user.getStyleClass().add("userIcon");
                    chatMessage.setSpacing(10);
                    chatMessage.getChildren().add(messageText);
                    chatMessage.getChildren().add(user);
                }
                ChatHistory.getChildren().add(chatMessage);
            }
            selectedChat = chatName;
            Platform.runLater(this::scrolltoBottom);
        }

        ChatTitle.setText(chatName);
    }

    private void scrolltoBottom() {
        ChatScroll.setVmin(0.0);
        ChatScroll.setVmax(1.0);
        ChatScroll.setVvalue(1.0);
    }

    private Button createAddButton() {
        SVGPath path = new SVGPath();
        path.setContent("M440-440H200v-80h240v-240h80v240h240v80H520v240h-80v-240Z");
        Button button = initToolButtons(path);
        button.setOnAction(event -> addChat());
        return button;
    }

    private Button createSettingsButton() {
        SVGPath path = new SVGPath();
        path.setContent("m370-80-16-128q-13-5-24.5-12T307-235l-119 50L78-375l103-78q-1-7-1-13.5v-27q0-6.5 1-13.5L78-585l110-190 119 50q11-8 23-15t24-12l16-128h220l16 128q13 5 24.5 12t22.5 15l119-50 110 190-103 78q1 7 1 13.5v27q0 6.5-2 13.5l103 78-110 190-118-50q-11 8-23 15t-24 12L590-80H370Zm70-80h79l14-106q31-8 57.5-23.5T639-327l99 41 39-68-86-65q5-14 7-29.5t2-31.5q0-16-2-31.5t-7-29.5l86-65-39-68-99 42q-22-23-48.5-38.5T533-694l-13-106h-79l-14 106q-31 8-57.5 23.5T321-633l-99-41-39 68 86 64q-5 15-7 30t-2 32q0 16 2 31t7 30l-86 65 39 68 99-42q22 23 48.5 38.5T427-266l13 106Zm42-180q58 0 99-41t41-99q0-58-41-99t-99-41q-59 0-99.5 41T342-480q0 58 40.5 99t99.5 41Zm-2-140Z");
        Button button = initToolButtons(path);
        button.setOnAction(event -> openSettings());
        return button;
    }

    private Button initToolButtons(SVGPath path) {
        Bounds bounds = path.getBoundsInLocal();
        double scaleFactor = 22 / Math.max(bounds.getWidth(), bounds.getHeight());
        path.setScaleX(scaleFactor);
        path.setScaleY(scaleFactor);
        path.getStyleClass().add("button-icon");
        Button button = new Button();
        button.setPickOnBounds(true);
        button.setGraphic(path);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("icon-button");
        return button;
    }

    @FXML
    protected void onChatMessage() {
        String message = ChatField.getText();
        message = message.trim();
        if (message.isEmpty() || selectedChat == null) {
            return;
        }
        ChatField.clear();
        HBox chatMessage = new HBox();
        chatMessage.setAlignment(Pos.CENTER_RIGHT);
        chatMessage.getStyleClass().add("message");
        chatMessage.setStyle("-fx-margin: 5 5 5 5;");
        ImageView userimg = new ImageView();
        userimg.setImage(new Image(getClass().getResource("/com/example/mysticmindfx/Images/profile-user.png").toString()));
        userimg.setFitHeight(39);
        userimg.setFitWidth(39);
        userimg.getStyleClass().add("userIcon");
        chatMessage.setSpacing(10);

        Text messageText = new Text(message);
        messageText.setStyle("-fx-fill: white;");
        messageText.getStyleClass().add("messageText");
        chatMessage.getChildren().add(messageText);
        chatMessage.getChildren().add(userimg);
        ChatHistory.getChildren().add(chatMessage);
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.saveHistory(selectedChat, ChatHistory, user);
        Platform.runLater(this::scrolltoBottom);
    }

    @FXML
    protected void clicked() {
        System.out.println("Clicked");
    }

    @FXML
    protected void openSettings() {
        System.out.println("Settings Clicked");
    }
}
