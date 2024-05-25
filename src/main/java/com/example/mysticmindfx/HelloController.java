package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField MailField;
    @FXML
    private TextField PasswordField;

    @FXML
    protected void onSignIn() {
        System.out.println("Mail: " + MailField.getText());
        System.out.println("Password: " + PasswordField.getText());
    }
}