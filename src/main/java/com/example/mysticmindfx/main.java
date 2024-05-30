package com.example.mysticmindfx;
import java.util.Scanner;

public class main {
//    static JSONHandler jsonHandler = new JSONHandler();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String input = scanner.nextLine().toLowerCase();
        ResourceSelector rs = new ResourceSelector();

// Splits de invoer in afzonderlijke woorden
        String[] words = input.split(" ");

// Initialiseer de variabelen
        String language = rs.determineLanguage(words);
        String category = rs.determineCategory(words);
        String naam = rs.determineName(words, language, category);
        String found = rs.selectDocumentation(language);
        if (!DocumentationProcessor.validateInputs(language, category, naam)) {
            return;
        }

        if (!DocumentationProcessor.validateDocumentation(found, language)) {
            return;
        }

        DocumentationProcessor.parseJSONAndPrintDescription(filename, found, category, input);
    }
}