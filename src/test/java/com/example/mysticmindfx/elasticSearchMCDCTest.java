package com.example.mysticmindfx;

import com.example.mysticmindfx.AIService.elasticSearch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Modified condition/decision Coverage
class elasticSearchMCDCTest {

    // Beide keywords zijn niet null
    @Test
    public void testValidateInputs_BothKeywordsNotNull() {
        String keyword1 = "keyword1";
        String keyword2 = "keyword2";

        boolean result = elasticSearch.validateInputs(keyword1, keyword2);

        assertTrue(result);
    }

    // Keyword1 is null, Keyword2 is niet null
    @Test
    public void testValidateInputs_Keyword1Null() {
        String keyword1 = null;
        String keyword2 = "keyword2";

        boolean result = elasticSearch.validateInputs(keyword1, keyword2);

        assertFalse(result);
    }

    // Keyword1 is niet null, Keyword2 is null
    @Test
    public void testValidateInputs_Keyword2Null() {
        String keyword1 = "keyword1";
        String keyword2 = null;

        boolean result = elasticSearch.validateInputs(keyword1, keyword2);

        assertFalse(result);
    }

    // Beide keywords zijn null
    @Test
    public void testValidateInputs_BothKeywordsNull() {
        String keyword1 = null;
        String keyword2 = null;

        boolean result = elasticSearch.validateInputs(keyword1, keyword2);

        assertFalse(result);
    }

}