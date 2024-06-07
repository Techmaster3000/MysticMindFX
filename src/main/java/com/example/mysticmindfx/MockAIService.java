package com.example.mysticmindfx;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MockAIService{

    private static Map<String,String>RESPONSES=Map.of(
            "social-platform-application", "Social Platform Application bevat kernfunctionaliteiten zoals gebruikersauthenticatie, profielbeheer en inhoudcreatie.",
            "financial-system", "Financial System bevat kernfunctionaliteiten zoals transactie verwerking, accountbeheer en rapportage.",

            "apache commons", "Apache Commons is een verzameling herbruikbare Java-componenten en hulpprogramma's.",
            "build tools", "Build tools zoals Apache Maven en Gradle worden gebruikt om het proces van het compileren, testen en verpakken van Java-applicaties te automatiseren. Ze beheren afhankelijkheden, verwerken projectconfiguraties en faciliteren continue integratie.",
            "object oriented", "Object-Oriented: Java ondersteunt klassen, objecten, overerving, encapsulatie en polymorfisme.",

            "pandas", "Pandas is een krachtige bibliotheek voor gegevensmanipulatie en -analyse in Python.",
            "interpreted", "Python-code wordt regel voor regel uitgevoerd door de interpreter, wat snelle ontwikkeling en eenvoudige foutopsporing mogelijk maakt.",
            "testing frameworks", "Python-ontwikkelaars gebruiken testframeworks zoals pytest, unittest en doctest om tests voor hun code te schrijven en uit te voeren. Deze frameworks helpen bij het waarborgen van de codekwaliteit, het opsporen van bugs en het faciliteren van codeonderhoud."
    );
    public String question(String question, String documentation) {
        String lowerCaseQuestion = question.toLowerCase();
        String lowerCaseDocumentation = documentation.toLowerCase();

        return RESPONSES.entrySet().stream()
                .filter(kv -> lowerCaseQuestion.contains(kv.getKey().toLowerCase()) || lowerCaseDocumentation.contains(kv.getKey().toLowerCase()))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining("\n"));
    }
}


