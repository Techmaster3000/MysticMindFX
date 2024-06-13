package com.example.mysticmindfx;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements IController {
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the stylesheet
        SceneSwitcher.getInstance().getMainStage().getScene().getStylesheets().add("com/example/mysticmindfx/Stylesheet.css");
    }

    @FXML
    protected void onBack() {
        // Go back to the previous scene
        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", null);
    }

    @FXML
    protected void onSignOut() {
        // Sign out
        SceneSwitcher.getInstance().removeUser();
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Login", null);
    }

    @FXML
    protected void onChange() {
        User user = SceneSwitcher.getInstance().getUser();
        if (user == null) {
            // Handle the case where no user is set
            System.out.println("No user is set in SceneSwitcher");
            return;
        }
        SceneSwitcher.getInstance().switchScene("ChangeAccountInfo.fxml", "Change Account Info", user.getEmail());
    }


}
