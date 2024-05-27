package com.example.mysticmindfx;

import javafx.fxml.FXML;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setAlignment(javafx.geometry.Pos.CENTER);
    }
}
