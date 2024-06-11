package com.example.mysticmindfx;

import javafx.fxml.FXML;

public class RenameController {
    @FXML
    private javafx.scene.control.TextField RenameField;

    @FXML
    protected void sendnewName() throws Exception{
        //send the new name to the existing MainController
        //return if the field is empty
        if (RenameField.getText().equals("")) {
            return;
        }
        MainController.getInstance().renameChat(RenameField.getText());
        //close the window
        RenameField.getScene().getWindow().hide();
    }
}
