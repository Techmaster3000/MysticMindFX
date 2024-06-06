package com.example.mysticmindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.ObjectInputFilter;

public class HelloController{
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


    }


}