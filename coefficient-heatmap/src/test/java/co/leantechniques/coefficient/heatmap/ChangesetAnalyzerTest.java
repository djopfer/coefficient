package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChangesetAnalyzerTest {

    private Map<String,Set<String>> results;
    private ChangesetAnalyzer analyzer;
    private CommitInfoBuilder builder;
    private CodeRepository mockRepository;
    private Set<Commit> givenCommits;

    @Before
    public void setUp() throws Exception {
        builder = CommitInfoBuilder.create();
        mockRepository = mock(CodeRepository.class);
        analyzer = new ChangesetAnalyzer(mockRepository);
        givenCommits = new HashSet<Commit>();
        when(mockRepository.getCommits()).thenReturn(givenCommits);
    }

    @Test
    public void organizesCommitsByStory() throws Exception {
        givenCommits.add(builder.author("joesmith").description("US1234 Some hokey Message").addFiles("File1.java").toCommit());
        
        results = analyzer.getFilesByStory();

        assertTrue(assertStoryPresent("US1234"));
        assertTrue(results.get("US1234").contains("File1.java"));
    }

    @Test
    public void isntConfusedByEmbeddedNewlines() throws Exception {
        String descriptionWithNewLines = "US1234 Some hokey Message".replaceAll(" ", System.getProperty("line.separator"));
        givenCommits.add(builder.author("joesmith").description(descriptionWithNewLines).addFiles("File1.java").toCommit());

        results = analyzer.getFilesByStory();

        assertTrue(assertStoryPresent("US1234"));
        assertTrue(results.get("US1234").contains("File1.java"));
    }

    @Test
    public void multipleFilesetsForTheSameStoryAreAggregated() throws Exception {
        givenCommits.add(builder.author("joesmith").description("US1234 Some message").addFiles("File1.java").toCommit());
        givenCommits.add(builder.author("joesmith").description("US1234 Some other message").addFiles("File2.java").toCommit());
        givenCommits.add(builder.author("joesmith").description("US1234 Some other message").addFiles("File1.java","File3.java").toCommit());

        results = analyzer.getFilesByStory();

        assertEquals(3, results.get("US1234").size());
        assertTrue(results.get("US1234").contains("File1.java"));
        assertTrue(results.get("US1234").contains("File2.java"));
        assertTrue(results.get("US1234").contains("File3.java"));
    }

    @Test
    public void treatsDefectsAsStories() throws Exception {
        givenCommits.add(builder.author("Bob Smith").description("DE1234 Some message").addFiles("File1.java").toCommit());
        results = analyzer.getFilesByStory();

        assertTrue(assertStoryPresent("DE1234"));
        assertTrue(results.get("DE1234").contains("File1.java"));
    }

//    @Test
//    public void groupChangesetsByAuthor(){
//        givenCommits.add(builder.author("joe smith").description("US1234 Some message").addFiles("File1.java").toCommit());
//        givenCommits.add(builder.author("joe smith").description("US1234 Some other message").addFiles("File2.java").toCommit());
//        givenCommits.add(builder.author("peter smith").description("US1234 Some other message").addFiles("File1.java", "File3.java").toCommit());
//
//        Map<String, Set<Commit>> resultsByAuthor = analyzer.groupByAuthor();
//
//        assertEquals(2, resultsByAuthor.get("joe smith").size());
//        assertEquals(1, resultsByAuthor.get("peter smith").size());
//    }

    @Test
    public void handlesMergeCommitsWithoutFiles(){
   		givenCommits.add(builder.author("joe smith").description("Merge").toCommit());
        
		results = analyzer.getFilesByStory();

        assertTrue(results.get("Unknown").size() == 0);
    }

    private boolean assertStoryPresent(String story) {
        return results.containsKey(story);
    }
}
