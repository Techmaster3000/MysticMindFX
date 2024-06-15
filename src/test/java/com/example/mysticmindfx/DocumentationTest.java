package com.example.mysticmindfx;

import com.example.mysticmindfx.AIService.ResourceSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DocumentationTest {
        // equivalence class + randwaarde
    @Test
    void testSelectDocumentationJava() {
        // Bekende taal: "java"
        String result = ResourceSelector.selectDocumentation("java");
        assertEquals("javaFoundDocumentation", result);
    }

    @Test
    void testSelectDocumentationJavaCaseInsensitive() {
        // Bekende taal: "JAVA" (randwaarde)
        String result = ResourceSelector.selectDocumentation("JAVA");
        assertEquals("javaFoundDocumentation", result);
    }

    @Test
    void testSelectDocumentationPython() {
        // Bekende taal: "python"
        String result = ResourceSelector.selectDocumentation("python");
        assertEquals("pythonFoundDocumentation", result);
    }

    @Test
    void testSelectDocumentationPythonCaseInsensitive() {
        // Bekende taal: "PYTHON" (randwaarde)
        String result = ResourceSelector.selectDocumentation("PYTHON");
        assertEquals("pythonFoundDocumentation", result);
    }

    @Test
    void testSelectDocumentationUnknownLanguage() {
        // Onbekende taal
        String result = ResourceSelector.selectDocumentation("ruby");
        assertNull(result);
    }

    @Test
    void testSelectDocumentationEmptyString() {
        // Lege string
        String result = ResourceSelector.selectDocumentation("");
        assertNull(result);
    }
}
