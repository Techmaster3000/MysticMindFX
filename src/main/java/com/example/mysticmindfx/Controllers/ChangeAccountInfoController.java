package com.example.mysticmindfx.Controllers;

import com.example.mysticmindfx.Controllers.IController;
import com.example.mysticmindfx.HistoryHandler;
import com.example.mysticmindfx.JSONHandler;
import com.example.mysticmindfx.SceneSwitcher;
import com.example.mysticmindfx.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeAccountInfoController implements IController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newEmailField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    private User currentUser;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiseer currentUser via SceneSwitcher
        currentUser = SceneSwitcher.getInstance().getUser();
        if (currentUser != null) {
            newUsernameField.setText(currentUser.getUsername());
            newEmailField.setText(currentUser.getEmail());
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (currentUser != null) {
            newUsernameField.setText(currentUser.getUsername());
            newEmailField.setText(currentUser.getEmail());
        }
    }

    @FXML
    protected void onSaveChanges() {
        if (currentUser == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No user logged in");
            alert.setContentText("Please log in before making changes.");
            alert.showAndWait();
            return;
        }
        String oldMail = currentUser.getEmail();

        String newUsername = newUsernameField.getText();
        String newEmail = newEmailField.getText();
        String oldPassword = oldPasswordField.getText();
        String enPw = org.apache.commons.codec.digest.DigestUtils.sha256Hex(oldPassword);
        String newPassword = newPasswordField.getText();

        // Check if the old password is correct
        if (!currentUser.checkPassword(enPw)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect password");
            alert.setContentText("The old password you entered is incorrect. Please try again.");
            alert.showAndWait();
            return;
        }

        JSONHandler.getInstance().updateUser(currentUser.getEmail(), newEmail, newUsername, newPassword);

        // Update the currentUser object
        currentUser = new User(newUsername, DigestUtils.sha256Hex(newPassword), newEmail);

        // Switch back to the settings screen
        HistoryHandler.userRename(oldMail, newEmail);
        SceneSwitcher.getInstance().setUser(currentUser);

        SceneSwitcher.getInstance().switchScene("Settings.fxml", "Settings", newEmail);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Changed");
        alert.setHeaderText("Your information has been successfully updated.");
        alert.setContentText("You can now log in with your new information. :)");
        alert.showAndWait();

    }

    @FXML
    protected void ToSettings() {
        SceneSwitcher.getInstance().switchScene("Settings.fxml", "Settings", null);
    }


}