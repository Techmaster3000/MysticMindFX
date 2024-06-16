package com.example.mysticmindfx.AIService;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;

public class GUIInteraction {

    public static ArrayList<String> domainModel(ArrayList<String> category) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Welk domain model wilt u zien?");
        alert.setTitle("Domain Model");

        ButtonType financialSystem = new ButtonType("Financial System");
        ButtonType socialPlatformApplication = new ButtonType("Social Platform Application");
        alert.getButtonTypes().setAll(financialSystem, socialPlatformApplication);

        alert.setContentText("Kies uw optie.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == financialSystem) {
                category.add("financial-system");
                category.remove(0);
            } else if (result.get() == socialPlatformApplication) {
                category.add("social-platform-application");
            } else {
                System.out.println("Geen keuze gemaakt.");
            }
        }

        return category;
    }
}
