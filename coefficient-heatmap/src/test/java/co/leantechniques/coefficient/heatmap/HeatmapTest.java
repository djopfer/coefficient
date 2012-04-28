package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

    private String reportFromHg;
    private ScmAdapter logCommand;
    private Heatmap heatmap;

    @Before
    public void setUp() throws Exception {
        logCommand = mock(HgLog.class);
        heatmap = new Heatmap(logCommand, new NullWriter());
    }

    // Test Split on || fails
    // Test message contains newlines
    @Test
    public void supportsCommitMessagesWithEmbeddedNewlines() {
        givenLogContains(commit("US1234 Message with" + System.getProperty("line.separator") + "embedded newline", "File1.java", "File2.java"));

        reportFromHg = heatmap.generate();

        assertReportContains("File1.java");
        assertReportContains("File2.java");
    }

    @Test
    public void reportsOnEachFileCommitted() {
        givenLogContains(commit("US1234 First message", "File1.java", "File2.java"),
                         commit("US4321 Second message", "File2.java", "File3.java"));

        reportFromHg = new Heatmap(logCommand, new NullWriter()).generate();

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

    @Test
    public void writesTheReportToTheClientSpecifiedWriter() {
        givenLogContains(commit("US1234 First message", "File1.java"));

        WriterSpy spy = new WriterSpy();
        new Heatmap(logCommand, spy).generate();

        assertEquals("write(),close(),", spy.logString);
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

    private class NullWriter extends Writer {
        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }

    private class WriterSpy extends Writer {

        public String logString = "";

        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
            logString += "write(),";
        }

        @Override
        public void flush() throws IOException {
            logString += "flush(),";
        }

        @Override
        public void close() throws IOException {
            logString += "close(),";
        }
    }
}
