package com.example.mysticmindfx;

import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneSwitcher {

    private static sceneSwitcher instance = null;
    private static Stage mainStage;

    private sceneSwitcher() {
    }

    public static sceneSwitcher getInstance() {
        if (instance == null) {
            instance = new sceneSwitcher();
        }
        return instance;
    }

    public void switchScene(String sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.mysticmindfx.HelloController.class.getResource(sceneName));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
}