package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

    private String commitData = "";
    private String reportFromHg;

    @Test
    public void reportsOnEachFileCommitted() {
        givenCommit("US1234 First message", "File1.java", "File2.java");
        givenCommit("US4321 Second message", "File2.java", "File3.java");

        HgLog logCommand = mock(HgLog.class);
        when(logCommand.execute()).thenReturn(new ByteArrayInputStream(commitData.getBytes()));

        reportFromHg = new Heatmap(logCommand).generateForRepository();

        assertReportContainsFile("File1.java");
        assertReportContainsFile("File2.java");
        assertReportContainsFile("File3.java");
    }

    @Test
    public void reportsSizeOfFilename() {
        givenCommit("US1234 First message", "File1.java");

        HgLog logCommand = mock(HgLog.class);
        when(logCommand.execute()).thenReturn(new ByteArrayInputStream(commitData.getBytes()));

        reportFromHg = new Heatmap(logCommand).generateForRepository();

        assertTrue(reportFromHg.contains("<div size='1'>File1.java</div>"));
    }

    @Test
    public void reportsSizeOfFileBasedOnNumberOfTotalAppearances() {
        givenCommit("US1234 First message", "File1.java");
        givenCommit("US4321 Second message", "File1.java");

        HgLog logCommand = mock(HgLog.class);
        when(logCommand.execute()).thenReturn(new ByteArrayInputStream(commitData.getBytes()));

        reportFromHg = new Heatmap(logCommand).generateForRepository();

        assertTrue(reportFromHg.contains("<div size='2'>File1.java</div>"));
    }

    private void assertReportContainsFile(String filename) {
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
