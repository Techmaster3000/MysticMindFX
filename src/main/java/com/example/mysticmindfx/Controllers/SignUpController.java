package com.example.mysticmindfx.Controllers;

import com.example.mysticmindfx.JSONHandler;
import com.example.mysticmindfx.Language;
import com.example.mysticmindfx.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements IController {
    @FXML
    private TextField UsernameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField CheckEmailField;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField CheckPasswordField;
    @FXML
    private Text usrNmExistsTxt;
    @FXML
    private Text emailExistsTxt;
    @FXML
    private Text EmailDupeText;
    @FXML
    private Text PasswordDupeText;
    @FXML
    private Text PasswordReqsText;
    @FXML
    private Text SignUpText;
    @FXML
    private VBox root;
    @FXML
    private Button SignInLink;
    @FXML
    private Button SignUpButton;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setAlignment(javafx.geometry.Pos.CENTER);
    }

    private void resetText() {
        usrNmExistsTxt.setText("");
        emailExistsTxt.setText("");
        EmailDupeText.setText("");
        PasswordDupeText.setText("");
        PasswordReqsText.setText("");
        SignUpText.setText("");
    }

    @FXML
    protected void onSignInLink() {
        if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
            System.out.println("Inloglink aangeklikt");
            SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Inloggen", null);
        } else {
            System.out.println("Sign In Link Clicked");
            SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Sign In", null);
        }
    }

    @FXML
    protected void onSignUp() {
        resetText();
        if (checkEmptyFields()) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                SignUpText.setText("Vul alle velden in.");
            } else {
                SignUpText.setText("Please fill in all fields.");
            }
            return;
        }
        if (JSONHandler.getInstance().findUser(EmailField.getText()) != null) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                emailExistsTxt.setText("Email bestaat al.");
            } else {
                emailExistsTxt.setText("Email already exists.");
            }
            return;
        }
        // Check if password contains at least 4 characters and 1 number
        if (PasswordField.getText().length() < 4 || !PasswordField.getText().matches(".*\\d.*")) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                PasswordReqsText.setText("Wachtwoord moet minimaal 4 tekens en 1 cijfer bevatten.");
            } else {
                PasswordReqsText.setText("Password must contain at least 4 characters and 1 number.");
            }
            return;
        }

        if (!EmailField.getText().equals(CheckEmailField.getText())) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                EmailDupeText.setText("Emails komen niet overeen.");
            } else {
                EmailDupeText.setText("Emails do not match.");
            }
            return;
        }
        if (!PasswordField.getText().equals(CheckPasswordField.getText())) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                PasswordDupeText.setText("Wachtwoorden komen niet overeen.");
            } else {
                PasswordDupeText.setText("Passwords do not match.");
            }
            return;
        }
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(PasswordField.getText());
        JSONHandler.getInstance().addJson(EmailField.getText(), UsernameField.getText(), hashedPassword);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
            alert.setTitle("Succesvolle aanmelding");
            alert.setHeaderText("Welkom " + UsernameField.getText());
            alert.setContentText("Je bent succesvol aangemeld!");
        } else {
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText("Welcome " + UsernameField.getText());
            alert.setContentText("You have successfully signed up!");
        }
        alert.showAndWait();
        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", EmailField.getText());
    }

    protected Boolean checkEmptyFields() {
        return UsernameField.getText().isEmpty() || EmailField.getText().isEmpty() || CheckEmailField.getText().isEmpty() || PasswordField.getText().isEmpty() || CheckPasswordField.getText().isEmpty();
    }
}
