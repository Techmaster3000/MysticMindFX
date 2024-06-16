package com.example.mysticmindfx.AIService;

import java.util.ArrayList;
import java.util.List;

public class ElasticSearchProcessor {

    public static String processDocumentation(String input, String filename) {
        try {
            elasticSearch es = new elasticSearch();
            List<String> allKeywords = es.alleKeyWords(filename);

            String[] words = input.split(" ");
            ArrayList<String> category = es.determineCategoryEs(words, allKeywords);

            if (category.contains("domain-model") && !category.contains("financial-system") && !category.contains("social-platform-application")) {
                category = GUIInteraction.domainModel(category);
            }

            return es.parseJSONAndPrintDocumentationEs(filename, category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
