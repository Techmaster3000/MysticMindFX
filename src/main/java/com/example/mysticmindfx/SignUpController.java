package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {
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
    @FXML
    protected void onSignInLink() {
        System.out.println("Sign In Link Clicked");
        SceneSwitcher.getInstance().switchScene("hello-view.fxml");

    }
    @FXML
    protected void onSignUp() {
        //TODO: Implement function to check account database if email already exists.
        //checkif any of the fields are empty
        if (UsernameField.getText().isEmpty() || EmailField.getText().isEmpty() || CheckEmailField.getText().isEmpty() || PasswordField.getText().isEmpty() || CheckPasswordField.getText().isEmpty()) {
            SignUpText.setText("Please fill in all fields.");
            return;
        }

    }

}
