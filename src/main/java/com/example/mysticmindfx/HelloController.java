package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements IController{
    @FXML
    private TextField mailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Text errorText;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpLink;
    @FXML
    private ImageView logo;
    @FXML
    private VBox sceneRoot;

    public HelloController() {
    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setText("");
    }

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
            System.out.println(mailField.getText() + " has logged in");
            SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", mailField.getText());
        }
        else {
            errorText.setText("Incorrect Password");
            passwordField.clear();
            return;
        }



    }
    @FXML
    protected void onSignUpLink() {
        System.out.println("Sign Up Link Clicked");
            SceneSwitcher.getInstance().switchScene("SignUp.fxml", "Sign Up", null);

    }
}