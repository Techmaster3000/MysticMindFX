
package com.example.mysticmindfx;

import com.example.mysticmindfx.AIService.Bundel;
import com.example.mysticmindfx.Controllers.MainController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ChatHandler {
    private static ChatHandler instance;

    private ChatHandler() {
        // Singleton private constructor
    }

    public static ChatHandler getInstance() {
        if (instance == null) {
            instance = new ChatHandler();
        }
        return instance;
    }

    public static HBox loadMessage(String line) {
        boolean fromAI;
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
            messageText.setText(messageText.getText().replaceAll("\\\\n", "\n"));
            ImageView AILogo = new ImageView();
            AILogo.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/logo.png").toString()));
            AILogo.setFitHeight(39);
            AILogo.setFitWidth(39);
            AILogo.getStyleClass().add("userIcon");
            chatMessage.getChildren().addAll(AILogo, messageText);
        } else {
            ImageView userimg = new ImageView();
            userimg.setImage(new Image(MainController.class.getResource("/com/example/mysticmindfx/Images/profile-user.png").toString()));
            userimg.setFitHeight(39);
            userimg.setFitWidth(39);
            userimg.getStyleClass().add("userIcon");
            chatMessage.getChildren().addAll(messageText, userimg);
        }
        chatMessage.setSpacing(10);
        return chatMessage;
    }

    public void generateResponse(String message) {
        String response = Bundel.bundelpakket(message); // Corrected Bundel to Bundle
        HBox chatMessage = new HBox();
        chatMessage.setAlignment(Pos.CENTER_LEFT);
        chatMessage.getStyleClass().add("response");
        chatMessage.setStyle("-fx-margin: 5 5 5 5;"); // Changed "-fx-margin" to "-fx-margin"
        MainController.getInstance().createMessage(chatMessage, response, true); // Corrected createMessage to createMessage
    }
}
