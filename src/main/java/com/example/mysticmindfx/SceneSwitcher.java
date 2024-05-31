package com.example.mysticmindfx;

import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    //singleton pattern
    private static SceneSwitcher instance = null;
    private static Stage mainStage;

    private SceneSwitcher() {
    }

    public static SceneSwitcher getInstance() {
        if (instance == null) {
            instance = new SceneSwitcher();
        }
        return instance;
    }

    public void switchScene(String sceneName, String WindowTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource(sceneName));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set window title
        mainStage.setTitle(WindowTitle);
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
    }
    public Stage getMainStage() {
        return mainStage;
    }
}
