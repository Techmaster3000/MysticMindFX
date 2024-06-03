package com.example.mysticmindfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bundel {
    public static void bundelpakket() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase();
        String filename = "src/main/java/com/example/mysticmindfx/programmingLanguage.json";
        String resource = RSprocessDocumentation(input, filename);
        String elastic = ESprocessDocumentation(input, filename);

        if (resource == null || elastic == null) {
            System.out.println("Geen documentatie gevonden.");
        } else {
            System.out.println("Prompt: " + input + "\n" );
            System.out.println("Resource Selector: " + resource + "\n");
            System.out.println("Elastic Search: " + elastic);
        }

    }

    public static String RSprocessDocumentation(String input, String filename) {
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

        return DocumentationProcessor.parseJSONAndPrintDescription(filename, found, category, input);
    }

    public static String ESprocessDocumentation(String input, String filename) {
        elasticSearch es = new elasticSearch();
        List<String> allKeywords = es.safastinkt(filename);
        // Splits de invoer in afzonderlijke woorden
        String[] words = input.split(" ");
        ArrayList<String> category = es.determineCategoryEs(words, allKeywords);
        String found = "elasticFoundDocumentation";

        System.out.println(category);
        return es.parseJSONAndPrintDocumentationEs(filename, category);
    }
}

