package com.example.mysticmindfx;

import com.example.mysticmindfx.AIService.DocumentationProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentationProcessorTest {

// multiple condition coverage --> method: validateInputs

    @Test
    public void testValidateInputsAllNonNull() {
        assertTrue(DocumentationProcessor.validateInputs("java", "Programming", "Example"));
    }

    @Test
    public void testValidateInputsLanguageNull() {
        assertFalse(DocumentationProcessor.validateInputs(null, "Programming", "Example"));
    }

    @Test
    public void testValidateInputsCategoryNull() {
        assertFalse(DocumentationProcessor.validateInputs("Java", null, "Example"));
    }

    @Test
    public void testValidateInputsNameNull() {
        assertFalse(DocumentationProcessor.validateInputs("Java", "Programming", null));
    }

    @Test
    public void testValidateInputsLanguageAndCategoryNull() {
        assertFalse(DocumentationProcessor.validateInputs(null, null, "Example"));
    }

    @Test
    public void testValidateInputsLanguageAndNameNull() {
        assertFalse(DocumentationProcessor.validateInputs(null, "Programming", null));
    }

    @Test
    public void testValidateInputsCategoryAndNameNull() {
        assertFalse(DocumentationProcessor.validateInputs("Java", null, null));
    }

    @Test
    public void testValidateInputsAllNull() {
        assertFalse(DocumentationProcessor.validateInputs(null, null, null));
    }
}
