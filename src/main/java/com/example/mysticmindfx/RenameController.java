package com.example.mysticmindfx;

import javafx.fxml.FXML;

public class RenameController {
    @FXML
    private javafx.scene.control.TextField RenameField;

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
