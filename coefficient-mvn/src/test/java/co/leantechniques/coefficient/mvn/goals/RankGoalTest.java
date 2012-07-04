package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.*;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RankGoalTest {
    @Mock Log mockLog;
    @Mock ChangesetAnalyzer mockChangesetAnalyzer;
    @Mock CodeRepository mockCodeRepository;
    private RankGoal goal;
    private Set<Commit> expectedCommits = new HashSet<Commit>();
    private AuthorStatisticSet expectedTenPercentReport = createExpectedReport();

    private AuthorStatisticSet createExpectedReport() {
        AuthorStatisticSet report = new AuthorStatisticSet();
        report.setTotalCommits(10);
        report.incrementTestedCommits();

        report.getCommitStatisticForAuthor("tim").setCountOfCommits(10);
        report.getCommitStatisticForAuthor("tim").setTestedCommits(10);

        report.getCommitStatisticForAuthor("rachel").setCountOfCommits(10);
        report.getCommitStatisticForAuthor("rachel").setTestedCommits(5);

        return report;
    }

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        goal = new RankGoal();
        when(mockCodeRepository.getCommits()).thenReturn(expectedCommits);
    }

    @Test
    public void shouldWriteGrandTotals() throws Exception {
        goal.setLog(mockLog);
        when(mockChangesetAnalyzer.getRankReport()).thenReturn(expectedTenPercentReport);
        goal.setChangesetAnalyzer(mockChangesetAnalyzer);

        goal.execute();

        verify(mockLog).info("10.00% of the 10 commits contain test files");
        verify(mockLog).info("100%\ttim\t\t\t(10 of 10 commits)");
        verify(mockLog).info("50%\trachel\t\t\t(5 of 10 commits)");
    }
}
