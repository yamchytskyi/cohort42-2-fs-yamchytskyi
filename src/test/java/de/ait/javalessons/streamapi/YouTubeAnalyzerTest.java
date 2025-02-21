package de.ait.javalessons.streamapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class YouTubeAnalyzerTest {

    @Test
    void testIsMore1M() {

        YouTubeAnalyzer youTubeAnalyzer = new YouTubeAnalyzer();

        boolean result = youTubeAnalyzer.isMore1M();

        assertTrue(result);
    }

}
