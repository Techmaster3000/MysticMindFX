package com.example.mysticmindfx.Controllers;

import com.example.mysticmindfx.*;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
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

    public static Text wrapText(Text text, String line) {
        if (line.length() > 50) {
            text.setWrappingWidth(400);
        }
        return text;
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
        user = SceneSwitcher.getInstance().getUser().getEmail();
    }

    public void setUser(String user) {
        this.user = user;
    }

    private void loadHistory() {
        File historyFolder = new File("src/chatHistory");

        HistoryHandler historyHandler = new HistoryHandler();
        ChatTabBox.getChildren().clear();
        for (File file : Objects.requireNonNull(historyFolder.listFiles())) {
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
        if (LanguageHandler.getInstance().getLanguage() == Language.DUTCH) {
            renamePopUpFile = renamePopUpFile.replace(".fxml", "-NL.fxml");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(renamePopUpFile));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.RENAME_CHAT));
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
        alert = new Alert(Alert.AlertType.CONFIRMATION, LanguageHandler.getInstance().getLocalizedMessage(Message.DELETE_CHAT_CONFIRMATION), ButtonType.YES, ButtonType.NO);
        alert.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.REMOVE_CHAT_TITLE));
        alert.setHeaderText(LanguageHandler.getInstance().getLocalizedMessage(Message.REMOVE_CHAT));

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
            if (LanguageHandler.getInstance().getLanguage() == Language.DUTCH) {
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
        String chatName = LanguageHandler.getInstance().getLanguage() == Language.DUTCH ? "Chat " + (ChatTabBox.getChildren().size() + 1) : "Chat " + (ChatTabBox.getChildren().size() + 1);
        Button newChat = new Button(chatName);
        newChat.getStyleClass().add("MenuItem");
        newChat.setStyle("-fx-text-fill: white;");
        newChat.setOnAction(event -> loadChat(chatName));
        ChatTabBox.getChildren().add(newChat);

        try {
            // Zorg ervoor dat de map bestaat
            File dir = new File("src/chatHistory");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Creëer het nieuwe chatbestand
            File file = new File("src/chatHistory/" + chatName + ".txt");
            if (file.exists()) {
                file = new File("src/chatHistory/" + chatName + "new" + ".txt");
            }
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(file);
                writer.write(user + "\n");
                writer.write(chatName + "\n");
                writer.close();
            } else {
                throw new IOException("Bestand bestaat al: " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Failed to create file: " + e.getMessage());
            // Toon een foutmelding aan de gebruiker
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (LanguageHandler.getInstance().getLanguage() == Language.DUTCH) {
                alert.setTitle("Fout bij aanmaken van chat");
                alert.setHeaderText("Kan chat niet aanmaken");
                alert.setContentText("Er is een fout opgetreden bij het aanmaken van de chat. Probeer het opnieuw.");
            } else {
                alert.setTitle("Error creating chat");
                alert.setHeaderText("Failed to create chat");
                alert.setContentText("An error occurred while creating the chat. Please try again.");
            }
            alert.showAndWait();
            // Verwijder de knop uit de ChatTabBox
            ChatTabBox.getChildren().remove(newChat);
            return;
        }

        loadChat(chatName);
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
                HBox chatMessage = ChatHandler.loadMessage(line);
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
        //create a user icon
        createMessage(chatMessage, message, false);

        ChatHandler.getInstance().generateResponse(message);

    }

    public void createMessage(HBox chatMessage, String message, Boolean fromAI) {
        ImageView userimg = new ImageView();
        Text messageText = new Text(message);
        messageText.setStyle("-fx-fill: white;");
        messageText.getStyleClass().add("messageText");
        if (fromAI) {
            userimg.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/logo.png").toString()));
            chatMessage.getChildren().add(userimg);
            chatMessage.getChildren().add(messageText);

        } else {
            userimg.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/profile-user.png").toString()));
            chatMessage.getChildren().add(messageText);
            chatMessage.getChildren().add(userimg);
        }
        userimg.setFitHeight(39);
        userimg.setFitWidth(39);
        userimg.getStyleClass().add("userIcon");
        chatMessage.setSpacing(10);

        //make the text white
        //set the max width of the text to 200
        wrapText(messageText, message);

        //make the text max width 200
        ChatHistory.getChildren().add(chatMessage);
        HistoryHandler historyHandler = new HistoryHandler();
        historyHandler.saveHistory(selectedChat, ChatHistory, user);
        Platform.runLater(this::scrolltoBottom);
    }


    @FXML
    protected void openSettings() {
        SceneSwitcher.getInstance().switchScene("Settings.fxml", "Settings", null);
    }
}
