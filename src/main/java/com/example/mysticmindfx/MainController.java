package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements IController {
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

    public MainController() {
        //initialize the controller
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        Button settingsButton = createSettingsButton();
        Button plusButton = createAddButton();
        ToolBar.getItems().add(settingsButton);
        ToolBar.getItems().add(plusButton);
        setChatHistory();
        ChatHistory.setSpacing(10);


    }
    private void setChatHistory() {
        File historyFolder = new File("src/chatHistory");

        HistoryHandler historyHandler = new HistoryHandler();
        ChatTabBox.getChildren().clear();
        for (File file : historyFolder.listFiles()) {
            ArrayList<String> chatHistory = historyHandler.retrieveHistory(file);
            Boolean fromAI = null;

            Button chat = new Button(chatHistory.get(0));
            chat.setOnAction(event -> loadChat(chat.getText()));
            chat.getStyleClass().add("MenuItem");
            //add the button to the scrollpane
            ChatTabBox.getChildren().add(chat);
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
                //add the message to the chat without the user: or AI: prefix
                if (fromAI) {
                    ImageView AILogo = new ImageView();
                    AILogo.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/logo.png").toString()));
                    AILogo.setFitHeight(39);
                    AILogo.setFitWidth(39);
                    AILogo.getStyleClass().add("userIcon");
                    chatMessage.getChildren().add(AILogo);
                    chatMessage.setSpacing(10);
                    chatMessage.getChildren().add(messageText);
                }
                else {
                    ImageView user = new ImageView();
                    user.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/profile-user.png").toString()));
                    user.setFitHeight(39);
                    user.setFitWidth(39);
                    user.getStyleClass().add("userIcon");
                    chatMessage.setSpacing(10);
                    chatMessage.getChildren().add(messageText);
                    chatMessage.getChildren().add(user);
                }
                ChatHistory.getChildren().add(chatMessage);
            }

        }



    }

    @FXML
    protected void addChat() {
        //add a button to the scrollpane
        Button newChat = new Button("Chat " + (ChatTabBox.getChildren().size() + 1));
        //set the button's style class to the same as the other buttons
        newChat.getStyleClass().add("MenuItem");
        //set the text to white
        newChat.setStyle("-fx-text-fill: white;");
        newChat.setOnAction(event -> loadChat(newChat.getText()));
        //add the button to the scrollpane
        ChatTabBox.getChildren().add(newChat);


    }
    private void loadChat(String chatName) {
        //highlight the chat that was clicked
        for (int i = 0; i < ChatTabBox.getChildren().size(); i++) {
            Button chat = (Button) ChatTabBox.getChildren().get(i);
            if (chat.getText().equals(chatName)) {
                chat.getStyleClass().add("selectedChat");
            } else {
                chat.getStyleClass().remove("selectedChat");
            }
        }
        //clear the chat history
        ChatHistory.getChildren().clear();
        HistoryHandler historyHandler = new HistoryHandler();
        //get the file where the first line is the chat name
        File folder = new File("src/chatHistory");
        File historyFile = null;
        for (File file : folder.listFiles()) {
            ArrayList<String> chatHistory = historyHandler.retrieveHistory(file);
            if (chatHistory.get(0).equals(chatName)) {
                historyFile = file;
                break;
            }
        }
        ArrayList<String> chatHistory = historyHandler.retrieveHistory(historyFile);
        Boolean fromAI = null;

        Button chat = new Button(chatHistory.get(0));
        chat.setOnAction(event -> loadChat(chat.getText()));
        chat.getStyleClass().add("MenuItem");
        //add the button to the scrollpane
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
                AILogo.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/logo.png").toString()));
                AILogo.setFitHeight(39);
                AILogo.setFitWidth(39);
                AILogo.getStyleClass().add("userIcon");
                chatMessage.getChildren().add(AILogo);
                chatMessage.setSpacing(10);
                chatMessage.getChildren().add(messageText);
            } else {
                ImageView user = new ImageView();
                user.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/profile-user.png").toString()));
                user.setFitHeight(39);
                user.setFitWidth(39);
                user.getStyleClass().add("userIcon");
                chatMessage.setSpacing(10);
                chatMessage.getChildren().add(messageText);
                chatMessage.getChildren().add(user);
            }
            ChatHistory.getChildren().add(chatMessage);
        }

    }

    private Button createAddButton() {
        SVGPath path = new SVGPath();
        path.setContent("M440-440H200v-80h240v-240h80v240h240v80H520v240h-80v-240Z");
        Button button = initButtons(path);
        //run the addChat method when the button is clicked
        button.setOnAction(event -> addChat());
        return button;
    }
    public void addHistory(ArrayList<String> message, String ChatName) {
        //create a button to represent the chat
        Button chatButton = new Button(ChatName);
        Boolean fromAI = null;
        //set the style class to the same as the other buttons
        chatButton.getStyleClass().add("MenuItem");
        //set the text to white
        chatButton.setStyle("-fx-text-fill: white;");
        //add the button to the sidebar
        ChatTabBox.getChildren().add(chatButton);
        //add the messages to the chathistory
        ChatHistory.getChildren().clear();
        for (String line : message) {
            HBox chatMessage = new HBox();
            if (line.startsWith("User: ")) {
                chatMessage.setAlignment(Pos.CENTER_RIGHT);
                chatMessage.getStyleClass().add("message");
                fromAI = false;
            } else if (line.startsWith("AI: ")){
                chatMessage.setAlignment(Pos.CENTER_LEFT);
                chatMessage.getStyleClass().add("response");
                fromAI = true;
            }
            Text messageText = new Text(line);
            messageText.setStyle("-fx-fill: white;");
            chatMessage.getChildren().add(messageText);
        }
    }

    private Button createSettingsButton() {
        SVGPath path = new SVGPath();
        path.setContent("m370-80-16-128q-13-5-24.5-12T307-235l-119 50L78-375l103-78q-1-7-1-13.5v-27q0-6.5 1-13.5L78-585l110-190 119 50q11-8 23-15t24-12l16-128h220l16 128q13 5 24.5 12t22.5 15l119-50 110 190-103 78q1 7 1 13.5v27q0 6.5-2 13.5l103 78-110 190-118-50q-11 8-23 15t-24 12L590-80H370Zm70-80h79l14-106q31-8 57.5-23.5T639-327l99 41 39-68-86-65q5-14 7-29.5t2-31.5q0-16-2-31.5t-7-29.5l86-65-39-68-99 42q-22-23-48.5-38.5T533-694l-13-106h-79l-14 106q-31 8-57.5 23.5T321-633l-99-41-39 68 86 64q-5 15-7 30t-2 32q0 16 2 31t7 30l-86 65 39 68 99-42q22 23 48.5 38.5T427-266l13 106Zm42-180q58 0 99-41t41-99q0-58-41-99t-99-41q-59 0-99.5 41T342-480q0 58 40.5 99t99.5 41Zm-2-140Z");
        initButtons(path);
        Button button = initButtons(path);
        //run the addChat method when the button is clicked
        button.setOnAction(event -> openSettings());
        return button;
    }

    private Button initButtons(SVGPath path) {
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
        //get the text from the chat field
        String message = ChatField.getText();
        //if the message is empty, return
        //remove leading and trailing whitespace
        message = message.trim();
        if (message.isEmpty()) {
            return;
        }
        //clear the chat field
        ChatField.clear();
        //add the message to the chat
        HBox chatMessage = new HBox();
        chatMessage.setAlignment(Pos.CENTER_RIGHT);
        //make the message look like the other messages
        chatMessage.getStyleClass().add("message");
        //set the margin to 5
        chatMessage.setStyle("-fx-margin: 5 5 5 5;");
        //create a user icon
        ImageView user = new ImageView();
        user.setImage(new Image(getClass().getResource("/com/example/mysticmindfx/profile-user.png").toString()));
        user.setFitHeight(39);
        user.setFitWidth(39);
        user.getStyleClass().add("userIcon");
        chatMessage.setSpacing(10);

        Text messageText = new Text(message);
        //make the text white
        messageText.setStyle("-fx-fill: white;");
        messageText.getStyleClass().add("messageText");
        chatMessage.getChildren().add(messageText);
        chatMessage.getChildren().add(user);
        ChatHistory.getChildren().add(chatMessage);
        ChatScroll.setVvalue(1.0);


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
