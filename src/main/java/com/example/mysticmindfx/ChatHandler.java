package com.example.mysticmindfx;

import com.example.mysticmindfx.Controllers.MainController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChatHandler {
    public static HBox loadMessage(String line) {
        Boolean fromAI;
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
        messageText.getStyleClass().add("messageText");
        MainController.wrapText(messageText, line);
        if (fromAI) {
            messageText.setText(messageText.getText().replaceAll("\\n", "\n"));
            ImageView AILogo = new ImageView();
            AILogo.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/logo.png").toString()));
            AILogo.setFitHeight(39);
            AILogo.setFitWidth(39);
            AILogo.getStyleClass().add("userIcon");
            chatMessage.getChildren().add(AILogo);
            chatMessage.setSpacing(10);
            chatMessage.getChildren().add(messageText);
        } else {
            ImageView userimg = new ImageView();
            userimg.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/profile-user.png").toString()));
            userimg.setFitHeight(39);
            userimg.setFitWidth(39);
            userimg.getStyleClass().add("userIcon");
            chatMessage.setSpacing(10);
            chatMessage.getChildren().add(messageText);
            chatMessage.getChildren().add(userimg);
        }
        return chatMessage;
    }

}
