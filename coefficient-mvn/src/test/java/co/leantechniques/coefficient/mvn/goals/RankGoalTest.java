package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.*;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RankGoalTest {
    @Mock Log mockLog;
    @Mock ChangesetAnalyzer mockChangesetAnalyzer;
    @Mock AdapterFactory mockAdapterFactory;
    private RankGoal goal;
    @Mock CodeRepository mockCodeRepository;
    private Set<Commit> expectedCommits = new HashSet<Commit>();
    private TestReport expectedTenPercentReport = createExpectedReport();

    private TestReport createExpectedReport() {
        TestReport report = new TestReport();
        report.setTotalCommits(10);
        report.incrementTestedCommits();

        report.getAuthorByName("tim").setCountOfCommits(10);
        report.getAuthorByName("tim").setTestedCommits(10);

        report.getAuthorByName("rachel").setCountOfCommits(10);
        report.getAuthorByName("rachel").setTestedCommits(5);

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
