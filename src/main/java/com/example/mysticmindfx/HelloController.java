package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelloController {
    @FXML
    private TextField MailField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Button SignUpLink;
    @FXML
    private Text errorText;

    @FXML
    protected void onSignIn() throws NoSuchAlgorithmException {
        errorText.setText("");
        System.out.println(MailField.getText());
        User user = JSONHandler.getInstance().findUser(MailField.getText());
        if (user == null) {
            errorText.setText("Email not found");
            return;
        }
        if (user.checkPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(PasswordField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome " + user.getUsername());
            alert.setContentText("You have successfully logged in!");
            alert.showAndWait();
        }
        else {
            errorText.setText("Incorrect Password");
            PasswordField.clear();
            return;
        }
        //TODO: goto main scene

    }
    @FXML
    protected void onSignUpLink() {
        System.out.println("Sign Up Link Clicked");
            SceneSwitcher.getInstance().switchScene("SignUp.fxml");

    }
}