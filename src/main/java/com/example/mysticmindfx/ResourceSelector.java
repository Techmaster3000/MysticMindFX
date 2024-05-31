package com.example.mysticmindfx;

public class ResourceSelector {

    public static String determineLanguage(String[] words) {
        for (String word : words) {
            if (word.equals("java") || word.equals("python")) {
                return word;
            }
        }
        return null;
    }
    public static String determineCategory(String[] words) {
        for (String word : words) {
            if (word.equals("features") || word.equals("libraries") || word.equals("tools")) {
                return word;
            }
        }
        return null;
    }
    public static String determineName(String[] words, String language, String category) {
        for (String word : words) {
            if (!word.equals(language) && !word.equals(category)) {
                return word;
            }
        }
        return null;
    }
    public static String selectDocumentation(String language) {
        if (language.equalsIgnoreCase("java")){
            return "foundDocumentation2";
        }
        else if (language.equalsIgnoreCase("python")){
            return "foundDocumentation1";
        }
        return null;
    }
}
