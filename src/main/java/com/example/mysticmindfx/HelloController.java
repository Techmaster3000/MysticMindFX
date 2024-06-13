package com.example.mysticmindfx;

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
        if (sceneRoot != null) {
            sceneRoot.setAlignment(javafx.geometry.Pos.CENTER);
        }
    }

    @FXML
    protected void onSignIn() {
        errorText.setText("");
        System.out.println(mailField.getText());
        User user = JSONHandler.getInstance().findUser(mailField.getText());
        if (user == null) {
            errorText.setText(SceneSwitcher.getInstance().getLocalizedMessage(Message.EMAIL_NOT_FOUND));
            return;
        }
        if (user.checkPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwordField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(SceneSwitcher.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_TITLE));
            alert.setHeaderText(SceneSwitcher.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_HEADER) + user.getUsername());
            alert.setContentText(SceneSwitcher.getInstance().getLocalizedMessage(Message.LOGIN_SUCCESS_CONTENT));
            alert.showAndWait();
            System.out.println(mailField.getText() + " has logged in");
            SceneSwitcher.getInstance().setUser(user);
            SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", mailField.getText());
        } else {
            errorText.setText(SceneSwitcher.getInstance().getLocalizedMessage(Message.INCORRECT_PASSWORD));
            passwordField.clear();
            return;
        }
    }

    @FXML
    protected void onSignUpLink() {
        System.out.println(SceneSwitcher.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK));
        SceneSwitcher.getInstance().switchScene("SignUp.fxml", SceneSwitcher.getInstance().getLocalizedMessage(Message.SIGN_UP_LINK), null);
    }

    @FXML
    protected void switchToEnglish() {
        SceneSwitcher.getInstance().setLanguage(Language.ENGLISH);
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Login", null);
    }

    @FXML
    protected void switchToDutch() {
        SceneSwitcher.getInstance().setLanguage(Language.DUTCH);
        SceneSwitcher.getInstance().switchScene("hello-view.fxml", "Inloggen", null);
    }
}
