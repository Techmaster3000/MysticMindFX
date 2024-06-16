package com.example.mysticmindfx.Controllers;

import com.example.mysticmindfx.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements IController {

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

    public HelloController() {}

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setText("");
        alignSceneRoot();
    }

    private void alignSceneRoot() {
        if (sceneRoot != null) {
            sceneRoot.setAlignment(javafx.geometry.Pos.CENTER);
        }
    }

    @FXML
    protected void onSignIn() {
        errorText.setText("");
        User user = findUserByEmail();

        if (user == null) {
            showError(Message.EMAIL_NOT_FOUND);
            return;
        }

        if (!validatePassword(user)) {
            return;
        }

        showLoginSuccessAlert(user);
        switchToMainMenu(user.getEmail());
    }

    private User findUserByEmail() {
        return JSONHandler.getInstance().findUser(mailField.getText());
    }

    private void showError(Message message) {
        errorText.setText(LanguageHandler.getInstance().getLocalizedMessage(message));
    }

    private boolean validatePassword(User user) {
        String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwordField.getText());
        if (!user.checkPassword(hashedPassword)) {
            showError(Message.INCORRECT_PASSWORD);
            passwordField.clear();
            return false;
        }
        return true;
    }

    private void showLoginSuccessAlert(User user) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_TITLE));
        alert.setHeaderText(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_HEADER) + user.getUsername());
        alert.setContentText(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_CONTENT));
        alert.showAndWait();
        System.out.println(mailField.getText() + " has logged in");
        SceneSwitcher.getInstance().setUser(user);
    }

    private void switchToMainMenu(String email) {
        SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", email);
    }

    @FXML
    protected void onSignUpLink() {
        System.out.println(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK));
        SceneSwitcher.getInstance().switchScene("SignUp.fxml", LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK), null);
    }

    @FXML
    protected void switchToEnglish() {
        switchLanguage(Language.ENGLISH);
    }

    @FXML
    protected void switchToDutch() {
        switchLanguage(Language.DUTCH);
    }

    private void switchLanguage(Language language) {
        LanguageHandler.getInstance().setLanguage(language);
        SceneSwitcher.getInstance().switchLanguage();
    }
}




//package com.example.mysticmindfx.Controllers;
//
//import com.example.mysticmindfx.*;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class HelloController implements IController {
//    @FXML
//    private TextField mailField;
//    @FXML
//    private TextField passwordField;
//    @FXML
//    private Text errorText;
//    @FXML
//    private Button signInButton;
//    @FXML
//    private Button signUpLink;
//    @FXML
//    private ImageView logo;
//    @FXML
//    private VBox sceneRoot;
//
//    public HelloController() {}
//
//    @FXML
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        errorText.setText("");
//        if (sceneRoot != null) {
//            sceneRoot.setAlignment(javafx.geometry.Pos.CENTER);
//        }
//    }
//
//    @FXML
//    protected void onSignIn() {
//        errorText.setText("");
//        System.out.println(mailField.getText());
//        User user = JSONHandler.getInstance().findUser(mailField.getText());
//        if (user == null) {
//            errorText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.EMAIL_NOT_FOUND));
//            return;
//        }
//        if (user.checkPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwordField.getText()))) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_TITLE));
//            alert.setHeaderText(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_HEADER) + user.getUsername());
//            alert.setContentText(LanguageHandler.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_CONTENT));
//            alert.showAndWait();
//            System.out.println(mailField.getText() + " has logged in");
//            SceneSwitcher.getInstance().setUser(user);
//            SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", mailField.getText());
//        } else {
//            errorText.setText(LanguageHandler.getInstance().getLocalizedMessage(Message.INCORRECT_PASSWORD));
//            passwordField.clear();
//        }
//    }
//
//    @FXML
//    protected void onSignUpLink() {
//        System.out.println(LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK));
//        SceneSwitcher.getInstance().switchScene("SignUp.fxml", LanguageHandler.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK), null);
//    }
//
//    @FXML
//    protected void switchToEnglish() {
//        LanguageHandler.getInstance().setLanguage(Language.ENGLISH);
//        SceneSwitcher.getInstance().switchLanguage();
//    }
//
//    @FXML
//    protected void switchToDutch() {
//        LanguageHandler.getInstance().setLanguage(Language.DUTCH);
//        SceneSwitcher.getInstance().switchLanguage();
//    }
//}
