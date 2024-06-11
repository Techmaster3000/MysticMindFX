package com.example.mysticmindfx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Bundel {
    public static String bundelpakket(String input) {
        MockAIService ai = new MockAIService();

        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String resource = RSprocessDocumentation(input, filename);
        String elastic = ESprocessDocumentation(input, filename);
        String antwoord = resource + "\n" + elastic;

        if (resource == null && elastic == null) {
            return "Geen documentatie gevonden.";
        } else {
            return ai.question(antwoord, input);
        }
    }

    public static String RSprocessDocumentation(String input, String filename) {
        try {
            ResourceSelector rs = new ResourceSelector();

            // Splits de invoer in afzonderlijke woorden
            String[] words = input.split(" ");

            // Initialiseer de variabelen
            String language = rs.determineLanguage(words);
            String category = rs.determineCategory(words);
            String naam = rs.determineName(words, language, category);
            String found = rs.selectDocumentation(language);

            if (!DocumentationProcessor.validateInputs(language, category, naam)) {
                return null;
            }

            if (!DocumentationProcessor.validateDocumentation(found, language)) {
                return null;
            }

            if (filename == null || found == null || category == null){
                return null;
            } else {
                return DocumentationProcessor.parseJSONAndPrintDescription(filename, found, category, input);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String ESprocessDocumentation(String input, String filename) {
        Scanner scanner = new Scanner(System.in);
        try {
            elasticSearch es = new elasticSearch();
            List<String> allKeywords = es.alleKeyWords(filename);
            // Splits de invoer in afzonderlijke woorden
            String[] words = input.split(" ");
            ArrayList<String> category = es.determineCategoryEs(words, allKeywords);
            if (category.contains("domain-model") && !category.contains("financial-system") && !category.contains("social-platform-application")){
                category = domainModel(category, scanner);

            }

            if (filename == null){
                return null;
            } else {
                return es.parseJSONAndPrintDocumentationEs(filename, category);
            }
        } catch (Exception e) {
            return null;
        } finally {
            scanner.close();
        }
    }
    public static ArrayList<String> domainModel(ArrayList<String> category, Scanner scanner){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Welk domain model wilt u zien?");
        alert.setTitle("Domain Model");
        //change the button text
        ButtonType financialSystem = new ButtonType("Financial System");
        ButtonType socialPlatformApplication = new ButtonType("Social Platform Application");
        alert.getButtonTypes().setAll(financialSystem, socialPlatformApplication);
        alert.setContentText("Kies uw optie.");
        Optional<ButtonType> result = alert.showAndWait();

        // Process the result
        if (result.isPresent() && result.get() == financialSystem) {
            category.add("financial-system");
            category.remove(0);
        } else if (result.isPresent() && result.get() == socialPlatformApplication) {
            category.add("social-platform-application");
        }
        else {
            // ... user chose CANCEL or closed the dialog
            System.out.println("Geen keuze gemaakt.");
        }

        return category;
    }
}

