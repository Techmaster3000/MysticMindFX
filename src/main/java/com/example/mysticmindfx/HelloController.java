package com.example.mysticmindfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HelloController {
    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {
        // Voeg een luisteraar toe aan de plusknop om een nieuw tabblad toe te voegen
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getGraphic() instanceof Button) {
                Button addButton = (Button) tab.getGraphic();
                addButton.setOnAction(event -> addNewTab());
            }
        }
    }

    private void addNewTab() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tabbladnaam");
        dialog.setHeaderText("Voer de naam van het tabblad in:");
        dialog.setContentText("Naam:");

        // Toon dialoogvenster voor het invoeren van de tabbladnaam
        dialog.showAndWait().ifPresent(name -> {
            Tab newTab = new Tab(name);
            addTabContent(newTab);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
            tabPane.getSelectionModel().select(newTab);
        });
    }

    private void addTabContent(Tab tab) {
        ListView<String> conversationView = new ListView<>();
        TextField inputField = new TextField();
        inputField.setPrefWidth(600); // Stel de breedte in op 200 pixels
        inputField.setPrefHeight(80); // Stel de hoogte in op 50 pixels

        inputField.setPromptText("Typ hier uw bericht...");
        Button sendButton = new Button("Verzenden");

        VBox chatBox = new VBox(10, conversationView, new HBox(10, inputField, sendButton));
        chatBox.setPadding(new Insets(10));

        sendButton.setOnAction(event -> {
            String message = inputField.getText().trim();
            if (!message.isEmpty()) {
                conversationView.getItems().add("U: " + message);
                inputField.clear();
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editNameItem = new MenuItem("Bewerk Naam");
        editNameItem.setOnAction(event -> {
            TextInputDialog nameDialog = new TextInputDialog(tab.getText());
            nameDialog.setTitle("Bewerk Tabbladnaam");
            nameDialog.setHeaderText("Voer de nieuwe naam van het tabblad in:");
            nameDialog.setContentText("Naam:");

            nameDialog.showAndWait().ifPresent(newName -> {
                tab.setText(newName);
            });
        });
        contextMenu.getItems().add(editNameItem);
        tab.setContextMenu(contextMenu);

        tab.setContent(chatBox);
    }
}