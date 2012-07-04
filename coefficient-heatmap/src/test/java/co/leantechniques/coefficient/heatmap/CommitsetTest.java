package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CommitsetTest {

    private Commitset commitset;

    @Before
    public void setUp() throws Exception {
        Set<Commit> expectedCommits = getExpectedCommits();
        commitset = new Commitset(expectedCommits);
    }

    @Test
    public void knowsAuthorsFromAllTheCommits(){
        AuthorStatisticSet result = commitset.getAuthorStatistics();

        assertThat(result.getTotalCommits(), equalTo(3));
        assertThat(result.getTotalTestedCommits(), equalTo(2));

        AuthorStatistic firstAuthorData = result.toList().get(0);
        assertThat(firstAuthorData.getAuthor(), equalTo("joe"));
        assertThat(firstAuthorData.getCountOfCommits(), equalTo(1));
        assertThat(firstAuthorData.getCountOfTestedCommits(), equalTo(1));
        assertThat(firstAuthorData.getPercentageOfTestedCommits(), equalTo(1.0));

        AuthorStatistic second = result.toList().get(1);
        assertThat(second.getAuthor(), equalTo("tim"));
        assertThat(second.getCountOfCommits(), equalTo(2));
        assertThat(second.getCountOfTestedCommits(), equalTo(1));
        assertThat(second.getPercentageOfTestedCommits(), equalTo(.5));
    }

    private Set<Commit> getExpectedCommits() {
        Set<Commit> expectedCommits = new HashSet<Commit>();
        expectedCommits.add(new Commit("tim", "US1000 first message", "file1.java", "file2.java"));
        expectedCommits.add(new Commit("tim", "US1000 Some message", "file1.java", "file1Test.java"));
        expectedCommits.add(new Commit("joe", "US2000 Joe wrote this message", "file2.java", "file2Test.java"));
        return expectedCommits;
    }
}
