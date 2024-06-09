package com.example.mysticmindfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bundel {
    public static void bundelpakket() {
        Scanner scanner = new Scanner(System.in);
        MockAIService ai = new MockAIService();
        String input = scanner.nextLine().toLowerCase();
        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String resource = RSprocessDocumentation(input, filename);
        String elastic = ESprocessDocumentation(input, filename);
        String antwoord = resource + " " + elastic;

        if (resource == null && elastic == null) {
            System.out.println("Geen documentatie gevonden.");
        } else {
            System.out.println(ai.question(antwoord, input));
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
            if (filename == null || category == null){
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
            System.out.println("Welk domain model wilt u zien?");
            System.out.println("1. Financial-system");
            System.out.println("2. Social-platform-application");
            String in = scanner.nextLine().toLowerCase();
            switch (in){
                case "1":
                    category.add("financial-system");
                    category.remove(0);
                    break;
                case "2":
                    category.add("social-platform-application");
                    break;
                default:
                    break;
            }

        return category;
    }
}

