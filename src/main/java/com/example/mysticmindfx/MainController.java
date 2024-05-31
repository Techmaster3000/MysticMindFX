package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button settings = new Button("settings_24dp_FILL0_wght400_GRAD0_opsz24.svg");
        settings.getStyleClass().add("MenuItem");
        ToolBar.getItems().add(settings);
    }
    @FXML
    protected void addChat() {
        //add a button to the scrollpane
        Button newChat = new Button("Chat " + (ChatTabBox.getChildren().size() + 1));
        //set the button's style class to the same as the other buttons
        newChat.getStyleClass().add("MenuItem");
        //add the button to the scrollpane
        ChatTabBox.getChildren().add(newChat);


    }
    @FXML
    protected void onChatMessage() {
        //get the text from the chat field
        String message = ChatField.getText();
        //clear the chat field
        ChatField.clear();
        //add the message to the chat
        HBox chatMessage = new HBox();
        chatMessage.getStyleClass().add("message");
        //create a user icon
        ImageView user = new ImageView();
        user.setImage(new Image(getClass().getResource("/com/example/mysticmindfx/profile-user.png").toString()));
        user.setFitHeight(39);
        user.setFitWidth(39);
        chatMessage.getChildren().add(user);
        Text messageText = new Text(message);
        messageText.getStyleClass().add("messageText");
        chatMessage.getChildren().add(messageText);
        ChatHistory.getChildren().add(chatMessage);


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
