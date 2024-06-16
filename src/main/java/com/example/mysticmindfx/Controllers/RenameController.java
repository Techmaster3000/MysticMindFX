package com.example.mysticmindfx.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RenameController {

    @FXML
    private TextField RenameField;

    @FXML
    protected void sendnewName() {
        // Return if the field is empty
        if (RenameField.getText().trim().isEmpty()) {
            return;
        }

        // Send the new name to the existing MainController
        MainController.getInstance().renameChat(RenameField.getText().trim());

        // Close the window
        RenameField.getScene().getWindow().hide();
    }
}



