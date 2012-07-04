package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AuthorStatisticSetTest {
    
    @Test
    public void sortByTestedPercentageDecending(){
        AuthorStatisticSet authorStatisticSet = new AuthorStatisticSet();
        AuthorStatistic annAtZeroPercentage = authorStatisticSet.getCommitStatisticForAuthor("ann");
        annAtZeroPercentage.incrementTotalCommitsBy(3);

        AuthorStatistic billAt50Percent = authorStatisticSet.getCommitStatisticForAuthor("bill");
        billAt50Percent.incrementTotalCommitsBy(2);
        billAt50Percent.incrementTestedCommits();

        AuthorStatistic charlieAt100Percent = authorStatisticSet.getCommitStatisticForAuthor("charlie");
        charlieAt100Percent.incrementTotalCommits();
        charlieAt100Percent.incrementTestedCommits();

        assertThat(authorStatisticSet.toList().get(0).getAuthor(), equalTo("charlie"));
        assertThat(authorStatisticSet.toList().get(1).getAuthor(), equalTo("bill"));
        assertThat(authorStatisticSet.toList().get(2).getAuthor(), equalTo("ann"));
    }
}
