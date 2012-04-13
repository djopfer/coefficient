package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

    private String commitData = "";
    private String reportFromHg;
    private HgLog logCommand;
    private Heatmap heatmap;

    @Before
    public void setUp() throws Exception {
        logCommand = mock(HgLog.class);
        heatmap = new Heatmap(logCommand);
    }

    @Test
    public void reportsOnEachFileCommitted() {
        givenCommit("US1234 First message", "File1.java", "File2.java");
        givenCommit("US4321 Second message", "File2.java", "File3.java");

        logShouldReturnCommits();

        reportFromHg = new Heatmap(logCommand).generate();

        assertReportContains("File1.java");
        assertReportContains("File2.java");
        assertReportContains("File3.java");
    }

    @Test
    public void reportsSizeOfFileBasedOnNumberOfTotalAppearances() {
        givenCommit("US1234 First message", "File1.java");
        givenCommit("US4321 Second message", "File1.java");

        logShouldReturnCommits();

        reportFromHg = heatmap.generate();

        assertReportContains("<div size='2'>File1.java</div>");
    }

    private void logShouldReturnCommits() {
        when(logCommand.execute()).thenReturn(new ByteArrayInputStream(commitData.getBytes()));
    }

    private void assertReportContains(String filename) {
        assertTrue(reportFromHg.contains(filename));
    }

    private void givenCommit(String message, String... files) {
        commitData += message + "||";
        for (String filename : files) {
            commitData += (filename + " ");
        }
        commitData += System.getProperty("line.separator");
    }

}
