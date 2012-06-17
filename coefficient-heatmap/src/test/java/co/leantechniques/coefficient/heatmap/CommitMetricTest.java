package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CommitMetricTest {

    private CommitMetric metric;

    @Before
    public void setUp() throws Exception {
        metric = new CommitMetric();
    }

    @Test
    public void knowsAuthorsFromAllTheCommits(){
        Set<Commit> expectedCommits = getExpectedCommits();

        TestReport result = metric.getReport(expectedCommits);

        assertThat(result.getTotalCommits(), equalTo(3));
        assertThat(result.getTotalTestedCommits(), equalTo(2));

        AuthorCommitInfo firstAuthorCommitData = result.getAuthors().get(0);
        assertThat(firstAuthorCommitData.getName(), equalTo("joe"));
        assertThat(firstAuthorCommitData.getCountOfCommits(), equalTo(1));
        assertThat(firstAuthorCommitData.getCountOfTestedCommits(), equalTo(1));
        assertThat(firstAuthorCommitData.getPercentageOfTestedCommits(), equalTo(1.0));

        AuthorCommitInfo secondAuthor = result.getAuthors().get(1);
        assertThat(secondAuthor.getName(), equalTo("tim"));
        assertThat(secondAuthor.getCountOfCommits(), equalTo(2));
        assertThat(secondAuthor.getCountOfTestedCommits(), equalTo(1));
        assertThat(secondAuthor.getPercentageOfTestedCommits(), equalTo(.5));
    }

    @Test
    public void knowsPercentageOfCommitsWithTestsPerAuthor(){
        Set<Commit> expectedCommits = getExpectedCommits();

        FilteredCommitSet result = metric.getTestedCommitsByAuthor(expectedCommits);

        assertEquals(3, result.getAllCommits().size());
        assertEquals(2, result.getTestedCommits().size());
        assertEquals(1, result.filterBy("tim").getTestedCommits().size());
        assertEquals(2, result.filterBy("tim").getAllCommits().size());
        assertEquals(1, result.filterBy("joe").getTestedCommits().size());
    }

    @Test
    public void knowsPercentageOfCommitsWithTestsPerStory(){
        Set<Commit> expectedCommits = getExpectedCommits();

        FilteredCommitSet result = metric.getTestedCommitsByStory(expectedCommits);

        assertEquals(3, result.getAllCommits().size());
        assertEquals(2, result.getTestedCommits().size());
        assertEquals(1, result.filterBy("US1000").getTestedCommits().size());
        assertEquals(2, result.filterBy("US1000").getAllCommits().size());
        assertEquals(1, result.filterBy("US2000").getTestedCommits().size());
    }

    private Set<Commit> getExpectedCommits() {
        Set<Commit> expectedCommits = new HashSet<Commit>();
        expectedCommits.add(new Commit("tim", "US1000 first message", "file1.java", "file2.java"));
        expectedCommits.add(new Commit("tim", "US1000 Some message", "file1.java", "file1Test.java"));
        expectedCommits.add(new Commit("joe", "US2000 Joe wrote this message", "file2.java", "file2Test.java"));
        return expectedCommits;
    }
}
