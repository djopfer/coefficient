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
        String commit = logLine("joesmith||US1234 Some hokey Message||File1.java");
        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertTrue(assertStoryPresent("US1234"));
        assertTrue(results.get("US1234").contains("File1.java"));
    }

    @Test
    public void isntConfusedByEmbeddedNewlines() throws Exception {
        String commit = logLine("joesmith||US1234 Some hokey Message||File1.java".replaceAll(" ", System.getProperty("line.separator")));
        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertTrue(assertStoryPresent("US1234"));
        assertTrue(results.get("US1234").contains("File1.java"));
    }

    @Test
    public void multipleFilesetsForTheSameStoryAreAggregated() throws Exception {
        String commit = logLine("joesmith||US1234 Some message||File1.java") +
                        logLine("joesmith||US1234 Some other message||File2.java") +
                        logLine("joesmith||US1234 Some other message||File1.java File3.java");

        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertEquals(3, results.get("US1234").size());
        assertTrue(results.get("US1234").contains("File1.java"));
        assertTrue(results.get("US1234").contains("File2.java"));
        assertTrue(results.get("US1234").contains("File3.java"));
    }

    @Test
    public void treatsDefectsAsStories() throws Exception {
        String commit = logLine("Bob Smith||DE1234 Some Message||File1.java");
        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertTrue(assertStoryPresent("DE1234"));
        assertTrue(results.get("DE1234").contains("File1.java"));
    }

    @Test
    public void groupChangesetsByAuthor(){
        String commit = logLine("joe smith||US1234 Some message||File1.java") +
                logLine("joe smith||US1234 Some other message||File2.java") +
                logLine("peter smith||US1234 Some other message||File1.java File3.java");

        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        Map<String, Set<Commit>> resultsByAuthor = analyzer.groupByAuthor();
        
        assertEquals(2, resultsByAuthor.get("joe smith").size());
        assertEquals(1, resultsByAuthor.get("peter smith").size());
    }

    @Test
    public void handlesMergeCommitsWithoutFiles(){
        String commit = logLine("joesmith||Merge||");
        analyzer = new ChangesetAnalyzer(streamFrom(commit), "||", "\\s+");
        results = analyzer.groupChangesetsByStory();

        assertTrue(results.get("Unknown").size() == 0);
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
