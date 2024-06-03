package com.example.mysticmindfx;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        RSprocessDocumentation();
        ESprocessDocumentation();
    }

    public static void RSprocessDocumentation() {
        Scanner scanner = new Scanner(System.in);
        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String input = scanner.nextLine().toLowerCase();
        ResourceSelector rs = new ResourceSelector();
        elasticSearch es = new elasticSearch();

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

    public static void ESprocessDocumentation() {
        Scanner scanner = new Scanner(System.in);
        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String input = scanner.nextLine().toLowerCase();
//        ResourceSelector rs = new ResourceSelector();
        elasticSearch es = new elasticSearch();
        List<String> allKeywords = es.safastinkt(filename);
        // Splits de invoer in afzonderlijke woorden
        String[] words = input.split(" ");
        ArrayList<String> category = es.determineCategoryEs(words, allKeywords);
        String found = "systemFoundDocumentation";

        System.out.println(category);
        es.parseJSONAndPrintDocumentationEs(filename, category);

//        if (!DocumentationProcessor.validateInputs(language, category)) {
//            return;
//        }
//
//        if (!DocumentationProcessor.validateDocumentation(found,  language)) {
//            return;
//        }
//
//        DocumentationProcessor.parseJSONAndPrintDescription(filename, found, category, input);
    }

}
