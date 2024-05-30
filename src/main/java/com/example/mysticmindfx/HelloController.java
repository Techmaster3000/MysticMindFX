package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;


public class HelloController {
    @FXML
    private TextField mailField;
    @FXML
    private TextField passwordField;
    /*@FXML
    private Button signUpLink;*/
    @FXML
    private Text errorText;

    @FXML
    protected void onSignIn(){
        errorText.setText("");
        System.out.println(mailField.getText());
        User user = JSONHandler.getInstance().findUser(mailField.getText());
        if (user == null) {
            errorText.setText("Email not found");
            return;
        }
        if (user.checkPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwordField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome " + user.getUsername());
            alert.setContentText("You have successfully logged in!");
            alert.showAndWait();
        }
        else {
            errorText.setText("Incorrect Password");
            passwordField.clear();
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