package com.example.mysticmindfx;

import com.example.mysticmindfx.Controllers.HelloController;
import com.example.mysticmindfx.Controllers.IController;
import com.example.mysticmindfx.Controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {
    // Singleton pattern
    private static SceneSwitcher instance = null;
    private static Stage mainStage;
    private static String user = null;
    private static String currentSceneFXML;
    private User currentUser;


    private SceneSwitcher() {
    }

    public static SceneSwitcher getInstance() {
        if (instance == null) {
            instance = new SceneSwitcher();
        }
        return instance;
    }

    public User getUser() {
        return currentUser;
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void switchLanguage() {
        //get the current scene
        switchScene(currentSceneFXML, mainStage.getTitle(), null);
    }

    public void switchScene(String sceneName, String windowTitle, String email) {
        try {
            String sceneFile = sceneName;
            if (LanguageHandler.getLanguage() == Language.DUTCH) {
                sceneFile = sceneFile.replace(".fxml", "-NL.fxml");
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFile));
            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);

            //Initialize the controller of the new scene regardless of classtype
            IController controller = loader.getController();


            //check if the controller is an instance of MainController
            if (controller instanceof MainController && email != null) {
                ((MainController) controller).setUser(user);
                if (user != null) {
                    ((MainController) controller).setUser(user);
                } else {
                    ((MainController) controller).setUser(email);
                }
            }

            // Check if the controller is an instance of ChangeAccountInfoController
            if (controller instanceof ChangeAccountInfoController) {
                User user = JSONHandler.getInstance().findUser(email);
                ((ChangeAccountInfoController) controller).setCurrentUser(user);
            }

            // Initialize the controller
            controller.initialize(null, null);

            mainStage.setTitle(windowTitle);
            mainStage.show();
            setCurrentSceneFXML(sceneName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentSceneFXML() {
        return currentSceneFXML;
    }

    public void setCurrentSceneFXML(String sceneFXML) {
        currentSceneFXML = sceneFXML;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public void removeUser() {
        user = null;
    }


}
