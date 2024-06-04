package com.example.mysticmindfx;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.stream.Collectors;

public class elasticSearch {


    public static boolean validateInputs(String keyword1, String keyword2) {
        if (keyword1 == null || keyword2 == null) {
            System.out.println("Onvoldoende informatie verstrekt. Probeer het opnieuw.");
            return false;
        }
        return true;
    }
    public static ArrayList<String> determineCategoryEs(String[] words, List<String> keywords) {
        ArrayList<String> categories = new ArrayList<>();
        for (String word : words) {
            if (keywords.contains(word)){
                categories.add(word);
            }
        }
        return categories.isEmpty() ? null : categories;
    }

        public static void vergelijkInput(ArrayList<String> categories, String input) {
            for (String category : categories) {

                }
            }
    public static List<String> safastinkt(String filePath){
        List<String> allKeywords = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON file
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            JSONArray foundDocumentation = jsonObject.getJSONArray("elasticFoundDocumentation");

            // Loop through each item in the array
            for (int i = 0; i < foundDocumentation.length(); i++) {
                JSONObject item = foundDocumentation.getJSONObject(i);
                JSONArray keywords = item.getJSONArray("keywords");

                // Add keywords to the list
                for (int j = 0; j < keywords.length(); j++) {
                    allKeywords.add(keywords.getString(j));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allKeywords;
    }
    public static String parseJSONAndPrintDocumentationEs(String filename, List<String> categories) {
        try {
            Object o = new JSONParser().parse(new FileReader(filename));
            org.json.simple.JSONObject j = (org.json.simple.JSONObject) o;

            org.json.simple.JSONArray foundDocumentation = (org.json.simple.JSONArray) j.get("elasticFoundDocumentation");
            for (Object doc : foundDocumentation) {
                org.json.simple.JSONObject docObj = (org.json.simple.JSONObject) doc;

                org.json.simple.JSONArray docKeywords = (org.json.simple.JSONArray) docObj.get("keywords");
                List<String> docKeywordsList = (List<String>) docKeywords.stream().map(kw -> ((String) kw).toLowerCase()).collect(Collectors.toList());

                if (categories.stream().anyMatch(cat -> docKeywordsList.contains(cat.toLowerCase()))) {
                    String documentation = (String) docObj.get("documentation");
                    return documentation;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

