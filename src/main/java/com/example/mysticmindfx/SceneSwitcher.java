package com.example.mysticmindfx;

import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;
public class SceneSwitcher {
    // Singleton pattern
    private static SceneSwitcher instance = null;
    private static Stage mainStage;
    private Language currentLanguage = Language.ENGLISH;

    private SceneSwitcher() {}

    public static SceneSwitcher getInstance() {
        if (instance == null) {
            instance = new SceneSwitcher();
        }
        return instance;
    }

    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    public Language getLanguage() {
        return currentLanguage;
    }

    public void switchScene(String sceneName, String windowTitle, String email) {
        try {
            String sceneFile = sceneName;
            if (currentLanguage == Language.DUTCH) {
                sceneFile = sceneFile.replace(".fxml", "-NL.fxml");
            }

            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource(sceneFile));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);
            mainStage.show();

            // Initialize the controller of the new scene regardless of classtype
            IController controller = loader.getController();

            // Check if the controller is an instance of MainController
            if (controller instanceof MainController) {
                ((MainController) controller).setUser(email);
            } else {
                System.out.println("Controller is not an instance of MainController");
            }
            controller.initialize(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainStage.setTitle(windowTitle);
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public Stage getMainStage() {
        return mainStage;
    }
}
