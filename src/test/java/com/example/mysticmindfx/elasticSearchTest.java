package com.example.mysticmindfx;

import com.example.mysticmindfx.AIService.elasticSearch;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

 // Equivalence Class + randwaarden
class elasticSearchTest {

    // Woord komt overeen met sleutelwoord
    @Test
    public void testDetermineCategoryEs_WordMatchesKeyword() {
        String[] words = {"word1", "word2", "word3"};
        List<String> keywords = Arrays.asList("word2", "word4");

        ArrayList<String> result = elasticSearch.determineCategoryEs(words, keywords);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains("word2"));
    }

    // Woord komt niet overeen met sleutelwoord
    @Test
    public void testDetermineCategoryEs_WordDoesNotMatchKeyword() {
        String[] words = {"word1", "word5", "word3"};
        List<String> keywords = Arrays.asList("word2", "word4");

        ArrayList<String> result = elasticSearch.determineCategoryEs(words, keywords);

        assertNull(result);
    }

    // Lege lijst van woorden
    @Test
    public void testDetermineCategoryEs_EmptyWordsList() {
        String[] words = {};
        List<String> keywords = Arrays.asList("word2", "word4");

        ArrayList<String> result = elasticSearch.determineCategoryEs(words, keywords);

        assertNull(result);
    }

    // Lijst van sleutelwoorden is leeg
    @Test
    public void testDetermineCategoryEs_EmptyKeywordsList() {
        String[] words = {"word1", "word2", "word3"};
        List<String> keywords = new ArrayList<>();

        ArrayList<String> result = elasticSearch.determineCategoryEs(words, keywords);

        assertNull(result);
    }

}