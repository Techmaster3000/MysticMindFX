package com.example.mysticmindfx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceSelectorTest {

    // decision coverage
    @Test
    void testDetermineLanguageWithJava() {
        String[] words = {"hello", "world", "java"};
        assertEquals("java", ResourceSelector.determineLanguage(words));
    }

    // decision coverage
    @Test
    void testDetermineLanguageWithNoMatch() {
        String[] words = {"hello", "world", "c++"};
        assertNull(ResourceSelector.determineLanguage(words));
    }

    // condition coverage
    @Test
    void testDetermineCategoryWithFeatures() {
        String[] words = {"hello", "features", "tools"};
        assertEquals("features", ResourceSelector.determineCategory(words));
    }

    // condition coverage
    @Test
    void testDetermineCategoryWithLibraries() {
        String[] words = {"hello", "libraries", "world"};
        assertEquals("libraries", ResourceSelector.determineCategory(words));
    }
}