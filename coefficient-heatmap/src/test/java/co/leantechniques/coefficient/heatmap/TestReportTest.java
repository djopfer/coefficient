package co.leantechniques.coefficient.heatmap;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Matchers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;

public class TestReportTest {
    
    @Test
    public void sortByTestedPercentageDecending(){
        TestReport report = new TestReport();
        AuthorCommitInfo annAtZeroPercentage = report.getAuthorByName("ann");
        annAtZeroPercentage.incrementTotalCommitsBy(3);

        AuthorCommitInfo billAt50Percent = report.getAuthorByName("bill");
        billAt50Percent.incrementTotalCommitsBy(2);
        billAt50Percent.incrementTestedCommits();

        AuthorCommitInfo charlieAt100Percent = report.getAuthorByName("charlie");
        charlieAt100Percent.incrementTotalCommits();
        charlieAt100Percent.incrementTestedCommits();

        assertThat(report.getAuthors().get(0).getName(), equalTo("charlie"));
        assertThat(report.getAuthors().get(1).getName(), equalTo("bill"));
        assertThat(report.getAuthors().get(2).getName(), equalTo("ann"));
    }
}
