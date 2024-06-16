package com.example.mysticmindfx.AIService;

public class ResourceSelector {

    public static String processDocumentation(String input, String filename) {
        try {
            String language = determineLanguage(input);
            String category = determineCategory(input);
            String name = determineName(input, language, category);
            String found = selectDocumentation(language);

            if (!isValidInputs(language, category, name, filename, found)) {
                return null;
            }

            return DocumentationProcessor.parseJSONAndPrintDescription(filename, found, category, input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String determineLanguage(String input) {
        String[] words = input.split(" ");
        for (String word : words) {
            if (word.equals("java") || word.equals("python")) {
                return word;
            }
        }
        return null;
    }

    private static String determineCategory(String input) {
        String[] words = input.split(" ");
        for (String word : words) {
            if (word.equals("features") || word.equals("libraries") || word.equals("tools")) {
                return word;
            }
        }
        return null;
    }

    private static String determineName(String input, String language, String category) {
        String[] words = input.split(" ");
        for (String word : words) {
            if (!word.equals(language) && !word.equals(category)) {
                return word;
            }
        }
        return null;
    }

    private static String selectDocumentation(String language) {
        if (language == null) {
            return null;
        }
        return language.equalsIgnoreCase("java") ? "javaFoundDocumentation" :
                language.equalsIgnoreCase("python") ? "pythonFoundDocumentation" :
                        null;
    }

    private static boolean isValidInputs(String language, String category, String name, String filename, String found) {
        return language != null && category != null && name != null && filename != null && found != null;
    }
}

