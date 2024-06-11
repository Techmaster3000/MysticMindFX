package com.example.mysticmindfx;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class DocumentationProcessor {

    public static boolean validateInputs(String language, String category, String name) {
        if (language == null || category == null || name == null) {
            System.out.println("Onvoldoende informatie verstrekt. Probeer het opnieuw.");
            return false;
        }
        return true;
    }

    public static boolean validateDocumentation(String found,  String language) {
        if (found == null){
            System.out.println("No documentation found for " + language);
            return false;
        }
        return true;
    }

    public static String parseJSONAndPrintDescription(String filename, String found, String category, String input) {
        try {
            Object o = new JSONParser().parse(new FileReader(filename));
            JSONObject j = (JSONObject) o;

            JSONArray foundDocumentation = (JSONArray) j.get(found);
            for (Object doc : foundDocumentation) {
                JSONObject docObj = (JSONObject) doc;

                JSONObject documentation = (JSONObject) docObj.get("documentation");
                JSONArray features = (JSONArray) documentation.get(category);

                for (Object feature : features) {
                    String name = (String) ((JSONObject) feature).get("name");
                    String description = (String) ((JSONObject) feature).get("description");
                    if (input.contains(name.toLowerCase())) {
                        return description;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
