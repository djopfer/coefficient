package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TestReportTest {
    
    @Test
    public void sortByTestedPercentageDecending(){
        Commitset commitset = new Commitset(null);
        AuthorCommitStatistic annAtZeroPercentage = commitset.getAuthorByName("ann");
        annAtZeroPercentage.incrementTotalCommitsBy(3);

        AuthorCommitStatistic billAt50Percent = commitset.getAuthorByName("bill");
        billAt50Percent.incrementTotalCommitsBy(2);
        billAt50Percent.incrementTestedCommits();

        AuthorCommitStatistic charlieAt100Percent = commitset.getAuthorByName("charlie");
        charlieAt100Percent.incrementTotalCommits();
        charlieAt100Percent.incrementTestedCommits();

        assertThat(commitset.getCommitStatistics().get(0).getAuthor(), equalTo("charlie"));
        assertThat(commitset.getCommitStatistics().get(1).getAuthor(), equalTo("bill"));
        assertThat(commitset.getCommitStatistics().get(2).getAuthor(), equalTo("ann"));
    }
}
