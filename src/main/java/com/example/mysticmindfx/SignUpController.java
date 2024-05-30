package com.example.mysticmindfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
        System.out.println("Sign In Link Clicked");
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Sign In");

    }
    @FXML
    protected void onSignUp() {
        resetText();
        if (checkEmptyFields()) {
            SignUpText.setText("Please fill in all fields.");
            return;
        }
        if (JSONHandler.getInstance().findUser(EmailField.getText()) != null) {
            emailExistsTxt.setText("Email already exists.");
            return;
        }
        //check if password contains at least 4 characters and 1 number
        if (PasswordField.getText().length() < 4 || !PasswordField.getText().matches(".*\\d.*")) {
            PasswordReqsText.setText("Password must contain at least 4 characters and 1 number.");
            return;
        }
        if (!EmailField.getText().equals(CheckEmailField.getText())) {
            EmailDupeText.setText("Emails do not match.");
            return;
        }
        if (!PasswordField.getText().equals(CheckPasswordField.getText())) {
            PasswordDupeText.setText("Passwords do not match.");
            return;
        }
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(PasswordField.getText());
        JSONHandler.getInstance().addJson(EmailField.getText(), UsernameField.getText(), hashedPassword);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Successful");
        alert.setHeaderText("Welcome " + UsernameField.getText());
        alert.setContentText("You have successfully signed up!");
        alert.showAndWait();
    }
    protected Boolean checkEmptyFields() {
        return UsernameField.getText().isEmpty() || EmailField.getText().isEmpty() || CheckEmailField.getText().isEmpty() || PasswordField.getText().isEmpty() || CheckPasswordField.getText().isEmpty();
    }

}
