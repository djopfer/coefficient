package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChangesetAnalyzer_getFilesByStoryTest {

    @InjectMocks
    private ChangesetAnalyzer analyzer;
    @Mock
    private CodeRepository mockRepository;

    private Set<Commit> givenCommits;
    private Map<String,Set<String>> results;

    @Before
    public void setUp() throws Exception {
        givenCommits = new HashSet<Commit>();
        when(mockRepository.getCommits()).thenReturn(givenCommits);
    }

    @Test
    public void organizesCommitsByStory() throws Exception {
        givenCommits.add(new Commit("joesmith","US1234 Some hokey Message","File1.java"));
        
        results = analyzer.getFilesByStory();

        assertThat(results.keySet(), hasItem("US1234"));
        assertThat(results.get("US1234"), hasItem("File1.java"));
    }

    @Test
    public void isntConfusedByEmbeddedNewlines() throws Exception {
        String descriptionWithNewLines = "US1234 Some hokey Message".replaceAll(" ", System.getProperty("line.separator"));
        givenCommits.add(new Commit("joesmith", descriptionWithNewLines, "File1.java"));

        results = analyzer.getFilesByStory();

        assertThat(results.keySet(), hasItem("US1234"));
        assertThat(results.get("US1234"), hasItem("File1.java"));
    }

    @Test
    public void multipleFilesetsForTheSameStoryAreAggregated() throws Exception {
        givenCommits.add(new Commit("joesmith","US1234 Some message","File1.java"));
        givenCommits.add(new Commit("joesmith","US1234 Some other message","File2.java"));
        givenCommits.add(new Commit("joesmith","US1234 Some other message","File1.java","File3.java"));

        results = analyzer.getFilesByStory();

        assertThat(results.get("US1234"), hasSize(3));
        assertThat(results.get("US1234"), hasItem("File1.java"));
        assertThat(results.get("US1234"), hasItem("File2.java"));
        assertThat(results.get("US1234"), hasItem("File3.java"));
    }

    @Test
    public void treatsDefectsAsStories() throws Exception {
        givenCommits.add(new Commit("Bob Smith","DE1234 Some message","File1.java"));
        results = analyzer.getFilesByStory();

        assertThat(results.keySet(), hasItem("DE1234"));
        assertThat(results.get("DE1234"), hasItem("File1.java"));
    }

    @Test
    public void handlesMergeCommitsWithoutFiles(){
   		givenCommits.add(new Commit("joe smith","Merge"));
        
		results = analyzer.getFilesByStory();

        assertThat(results.get("Unknown").size(), equalTo(0));
    }
}
