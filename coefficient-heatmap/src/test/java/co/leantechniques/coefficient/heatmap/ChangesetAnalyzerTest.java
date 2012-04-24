package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ChangesetAnalyzerTest {

    private Map<String,Set<String>> results;
    private ChangesetAnalyzer analyzer;

    @Test
    public void organizesCommitsByStory() throws Exception {
        String commit = logLine("US1234 Some honkey Message||File1.java");
        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertTrue(assertStoryPresent("US1234"));
        assertTrue(results.get("US1234").contains("File1.java"));
    }

    @Test
    public void multipleFilesetsForTheSameStoryAreAggregated() throws Exception {
        String commit = logLine("US1234 Some message||File1.java") +
                        logLine("US1234 Some other message||File2.java") +
                        logLine("US1234 Some other message||File1.java File3.java");

        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertEquals(3, results.get("US1234").size());
        assertTrue(results.get("US1234").contains("File1.java"));
        assertTrue(results.get("US1234").contains("File2.java"));
        assertTrue(results.get("US1234").contains("File3.java"));
    }

    private String logLine(String logText) {
        return logText + System.getProperty("line.separator");
    }

    private ByteArrayInputStream streamFrom(String commit) {
        return new ByteArrayInputStream(commit.getBytes());
    }

    private boolean assertStoryPresent(String story) {
        return results.containsKey(story);
    }
}
