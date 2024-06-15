package com.example.mysticmindfx.AIService;

public class Bundel {

    public static String bundelpakket(String input) {
        MockAIService ai = new MockAIService();

        String filename = "src/main/java/com/example/mysticmindfx/AIService/programmingLanguage.json";
        String resource = ResourceSelector.processDocumentation(input, filename);
        String elastic = ElasticSearchProcessor.processDocumentation(input, filename);
        String antwoord = resource + "\n" + elastic;

        if (resource == null && elastic == null) {
            return "Geen documentatie gevonden.";
        } else {
            return ai.question(antwoord, input);
        }
    }
}


