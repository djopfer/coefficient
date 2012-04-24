package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

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
        givenLogContains(commit("US1234 First message", "File1.java", "File2.java"),
                         commit("US4321 Second message", "File2.java", "File3.java"));

        reportFromHg = new Heatmap(logCommand).generate();

        assertReportContains("File1.java");
        assertReportContains("File2.java");
        assertReportContains("File3.java");
    }

    @Ignore
    @Test
    public void multipleCommitsForTheSameTicketAreTreatedAsSingleChange() {
        givenLogContains(commit("US1234 First message", "File1.java"),
                         commit("US1234 Second message", "File1.java"));

        reportFromHg = heatmap.generate();

        assertReportContains("<li style='foo'>File1.java</li>");
    }

    private void givenLogContains(String... commits) {
        String commitData = "";
        for (String commit : commits) {
            commitData += commit;
        }
        when(logCommand.execute()).thenReturn(new ByteArrayInputStream(commitData.getBytes()));
    }

    private void assertReportContains(String filename) {
        assertTrue(reportFromHg.contains(filename));
    }

    public static String commit(String message, String... files) {
        String commitData = message + "||";
        for (String filename : files) {
            commitData += (filename + " ");
        }
        commitData += System.getProperty("line.separator");
        return commitData;
    }

}
