package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.AuthorStatisticSet;
import co.leantechniques.coefficient.heatmap.ChangesetAnalyzer;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RankGoalTest {
    @InjectMocks private RankGoal goal;
    @Mock Log mockLog;
    @Mock ChangesetAnalyzer mockChangesetAnalyzer;

    @Before
    public void setup(){
    }

    @Test
    public void shouldWriteGrandTotals() throws Exception {
        when(mockChangesetAnalyzer.getAuthorStatistics()).thenReturn(createExpectedAuthorStatsHaveTests());

        goal.execute();

        verify(mockLog).info("5.00% of the 20 commits contain test files");
        verify(mockLog).info("100%\ttim\t\t\t(10 of 10 commits)");
        verify(mockLog).info("50%\trachel\t\t\t(5 of 10 commits)");
    }

    private AuthorStatisticSet createExpectedAuthorStatsHaveTests() {
        AuthorStatisticSet report = new AuthorStatisticSet();
        report.setTotalCommits(20);
        report.incrementTestedCommits();

        report.getCommitStatisticForAuthor("tim").setCountOfCommits(10);
        report.getCommitStatisticForAuthor("tim").setTestedCommits(10);

        report.getCommitStatisticForAuthor("rachel").setCountOfCommits(10);
        report.getCommitStatisticForAuthor("rachel").setTestedCommits(5);

        return report;
    }
}
