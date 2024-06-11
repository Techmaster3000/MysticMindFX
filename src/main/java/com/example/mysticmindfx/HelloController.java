package com.example.mysticmindfx;

import javafx.event.ActionEvent;
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
    private AnchorPane LogoutPane;

    Stage stage;

    public void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Are you sure you want to logout? ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) LogoutPane.getScene().getWindow();
            stage.close(); // dit veranderen met code om terug te gaan naar het inlogscherm (stage.setScene(loginScene))
        }
    }

    public void switchscene(ActionEvent event){
        sceneSwitcher.getInstance().switchScene("wwWijzigen.fxml");
    }




    @FXML
    private PasswordField NewWachtwoord;

    @FXML
    private PasswordField NogmaalsNewWW;

    @FXML
    private PasswordField OudWachtwoord;

    private PasswordData passwordData;

    @FXML
    private void handleChangePassword(ActionEvent event) {
        String OudWW = OudWachtwoord.getText();
        String NewWW = NewWachtwoord.getText();
        String NogmaalsWW = NogmaalsNewWW.getText();


        // Laad de wachtwoordgegevens
        passwordData = PasswordManager2.loadPasswordData();

        // Check of het ingevoerde oude wachtwoord overeenkomt met het opgeslagen versleutelde wachtwoord
        if (!PasswordManager.checkPassword(OudWW, passwordData.getOldPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Current password is incorrect!");
            alert.show();
            return;
        }


        // checkt op ingevulde wachtwoorden overeen komen
        if (!NewWW.equals(NogmaalsWW)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "New passwords do not match!");
            alert.show();
            return;
        }



        // Update het wachtwoord
        passwordData.setOldPassword(NewWW);

        // Sla het nieuwe wachtwoord op
        PasswordManager.savePasswordData(passwordData);


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password successfully changed!");
        alert.show();

    @FXML
    protected void onSignIn(){
        errorText.setText("");
        System.out.println(mailField.getText());
        User user = JSONHandler.getInstance().findUser(mailField.getText());
        if (user == null) {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                errorText.setText("Email niet gevonden");
            } else {
                errorText.setText("Email not found");
            }
            return;
        }
        if (user.checkPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(passwordField.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                alert.setTitle("Succesvolle login");
                alert.setHeaderText("Welkom " + user.getUsername());
                alert.setContentText("Je bent succesvol ingelogd!");
            } else {
                alert.setTitle("Login Successful");
                alert.setHeaderText("Welcome " + user.getUsername());
                alert.setContentText("You have successfully logged in!");
            }
            alert.showAndWait();
            System.out.println(mailField.getText() + " has logged in");
            SceneSwitcher.getInstance().switchScene("MainMenu.fxml", "MysticMind", mailField.getText());
        } else {
            if (SceneSwitcher.getInstance().getLanguage() == Language.DUTCH) {
                errorText.setText("Onjuist wachtwoord");
            } else {
                errorText.setText("Incorrect Password");
            }
            passwordField.clear();
            return;
        }
    }
    @FXML
    protected void onSignUpLink() {
        String language = SceneSwitcher.getInstance().getLanguage() == Language.DUTCH ? "NL" : "EN";

        if (language.equals("NL")) {
            System.out.println("Aanmeldlink aangeklikt");
        } else {
            System.out.println("Sign Up Link Clicked");
        }

        String sceneTitle = language.equals("NL") ? "Aanmelden" : "Sign Up";

        SceneSwitcher.getInstance().switchScene("SignUp.fxml", sceneTitle, null);
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