package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField MailField;
    @FXML
    private TextField PasswordField;
    @FXML
    private Button SignUpLink;

    @FXML
    protected void onSignIn() {
        System.out.println("Sign In Button Clicked");
        User user = JSONHandler.findUser(MailField.getText());
        user.checkPassword(PasswordField.getText());

    }
    @FXML
    protected void onSignUpLink() throws IOException {
        System.out.println("Sign Up Link Clicked");
            SceneSwitcher.getInstance().switchScene("SignUp.fxml");

    }
}