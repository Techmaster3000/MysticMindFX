package com.example.mysticmindfx.Controllers;

import com.example.mysticmindfx.*;
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

    // Extracted method to reset all text fields
    private void resetTextFields() {
        usrNmExistsTxt.setText("");
        emailExistsTxt.setText("");
        EmailDupeText.setText("");
        PasswordDupeText.setText("");
        PasswordReqsText.setText("");
        SignUpText.setText("");
    }

    @FXML
    protected void onSignInLink() {
        System.out.println(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_IN_LINK));
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_IN_LINK), null);
    }

    @FXML
    protected void onSignUp() {
        resetTextFields(); // Call to the extracted method to reset text fields

        // Validate form input
        if (validateSignUp()) {
            return;
        }

        // Process successful signup
        processSignUp();
    }

    // Extracted method to validate form input
    private boolean validateSignUp() {
        if (checkEmptyFields()) {
            SignUpText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.FILL_ALL_FIELDS));
            return true;
        }
        if (JSONHandler.getInstance().findUser(EmailField.getText()) != null) {
            emailExistsTxt.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.EMAIL_EXISTS));
            return true;
        }
        if (PasswordField.getText().length() < 4 || !PasswordField.getText().matches(".*\\d.*")) {
            PasswordReqsText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.PASSWORD_REQS));
            return true;
        }
        if (!EmailField.getText().equals(CheckEmailField.getText())) {
            EmailDupeText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.EMAILS_DO_NOT_MATCH));
            return true;
        }
        if (!PasswordField.getText().equals(CheckPasswordField.getText())) {
            PasswordDupeText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.PASSWORDS_DO_NOT_MATCH));
            return true;
        }
        return false;
    }

    // Extracted method to process successful signup
    private void processSignUp() {
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(PasswordField.getText());
        JSONHandler.getInstance().addJson(EmailField.getText(), UsernameField.getText(), hashedPassword);
        showSignUpSuccessAlert();
        User newUser = new User(UsernameField.getText(), hashedPassword, EmailField.getText());
        SceneSwitcher.getInstance().setUser(newUser);
        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", EmailField.getText());
    }

    // Extracted method to show signup success alert
    private void showSignUpSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_TITLE));
        alert.setHeaderText(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_HEADER) + UsernameField.getText());
        alert.setContentText(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_CONTENT));
        alert.showAndWait();
    }

    protected Boolean checkEmptyFields() {
        return UsernameField.getText().isEmpty() || EmailField.getText().isEmpty() || CheckEmailField.getText().isEmpty() || PasswordField.getText().isEmpty() || CheckPasswordField.getText().isEmpty();
    }
}



//package com.example.mysticmindfx.Controllers;
//
//
//import com.example.mysticmindfx.*;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class SignUpController implements IController {
//    @FXML
//    private TextField UsernameField;
//    @FXML
//    private TextField EmailField;
//    @FXML
//    private TextField CheckEmailField;
//    @FXML
//    private TextField PasswordField;
//    @FXML
//    private TextField CheckPasswordField;
//    @FXML
//    private Text usrNmExistsTxt;
//    @FXML
//    private Text emailExistsTxt;
//    @FXML
//    private Text EmailDupeText;
//    @FXML
//    private Text PasswordDupeText;
//    @FXML
//    private Text PasswordReqsText;
//    @FXML
//    private Text SignUpText;
//    @FXML
//    private VBox root;
//    @FXML
//    private Button SignInLink;
//    @FXML
//    private Button SignUpButton;
//
//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        root.setAlignment(javafx.geometry.Pos.CENTER);
//    }
//
//    private void resetText() {
//        usrNmExistsTxt.setText("");
//        emailExistsTxt.setText("");
//        EmailDupeText.setText("");
//        PasswordDupeText.setText("");
//        PasswordReqsText.setText("");
//        SignUpText.setText("");
//    }
//
//    @FXML
//    protected void onSignInLink() {
//        System.out.println(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_IN_LINK));
//        SceneSwitcher.getInstance().switchScene("hello-view.fxml", LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_IN_LINK), null);
//    }
//
//    @FXML
//    protected void onSignUp() {
//        resetText();
//        if (checkEmptyFields()) {
//            SignUpText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.FILL_ALL_FIELDS));
//            return;
//        }
//        if (JSONHandler.getInstance().findUser(EmailField.getText()) != null) {
//            emailExistsTxt.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.EMAIL_EXISTS));
//            return;
//        }
//        // Check if password contains at least 4 characters and 1 number
//        if (PasswordField.getText().length() < 4 || !PasswordField.getText().matches(".*\\d.*")) {
//            PasswordReqsText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.PASSWORD_REQS));
//            return;
//        }
//
//        if (!EmailField.getText().equals(CheckEmailField.getText())) {
//            EmailDupeText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.EMAILS_DO_NOT_MATCH));
//            return;
//        }
//        if (!PasswordField.getText().equals(CheckPasswordField.getText())) {
//            PasswordDupeText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.PASSWORDS_DO_NOT_MATCH));
//            return;
//        }
//        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(PasswordField.getText());
//        JSONHandler.getInstance().addJson(EmailField.getText(), UsernameField.getText(), hashedPassword);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_TITLE));
//        alert.setHeaderText(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_HEADER) + UsernameField.getText());
//        alert.setContentText(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_SUCCESS_CONTENT));
//        alert.showAndWait();
//        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", EmailField.getText());
//    }
//
//    protected Boolean checkEmptyFields() {
//        return UsernameField.getText().isEmpty() || EmailField.getText().isEmpty() || CheckEmailField.getText().isEmpty() || PasswordField.getText().isEmpty() || CheckPasswordField.getText().isEmpty();
//    }
//}
