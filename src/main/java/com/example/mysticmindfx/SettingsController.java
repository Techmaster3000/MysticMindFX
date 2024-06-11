package com.example.mysticmindfx;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements IController {
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set the stylesheet
        SceneSwitcher.getInstance().getMainStage().getScene().getStylesheets().add("com/example/mysticmindfx/Stylesheet.css");
    }
    @FXML
    protected void onBack() {
        //go back to the previous scene
        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind",null);
    }
    @FXML
    protected void changeAccountInfo() {
        //save the settings
    }
    @FXML
    protected void onSignOut() {
        //sign out
        SceneSwitcher.getInstance().removeUser();
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Login",null);

    }


}
