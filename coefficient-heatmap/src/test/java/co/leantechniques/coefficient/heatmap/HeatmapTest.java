package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

    private String reportFromHg;
    private Heatmap heatmap;
    private CodeRepository mockRepository;
    private CommitInfoBuilder builder;

    @Before
    public void setUp() throws Exception {
        mockRepository = mock(CodeRepository.class);
        heatmap = new Heatmap(mockRepository, new NullWriter());
        builder = CommitInfoBuilder.create();
    }

    @Test
    public void supportsCommitMessagesWithEmbeddedNewlines() {
        String descriptionWithNewLines = "US1234 Message with" + System.getProperty("line.separator") + "embedded newline";
        givenLogContains(builder.author("tim").description(descriptionWithNewLines).addFiles("File1.java", "File2.java").toCommit());

        reportFromHg = heatmap.generate();

        assertReportContains("File1.java");
        assertReportContains("File2.java");
    }

    @Test
    public void reportsOnEachFileCommitted() {
        givenLogContains(
                builder.author("tim").description("US1234 First message").addFiles("File1.java", "File2.java").toCommit(),
                builder.author("tim").description("").addFiles("File2.java", "File3.java").toCommit());

        reportFromHg = heatmap.generate();

        assertReportContains("File1.java");
        assertReportContains("File2.java");
        assertReportContains("File3.java");
    }

    @Test
    public void multipleCommitsForTheSameTicketAreTreatedAsSingleChange() {
        givenLogContains(
                builder.author("tim").description("US1234 First message").addFiles("File1.java").toCommit(),
                builder.author("tim").description("US1234 Second message").addFiles("File1.java").toCommit());

        assertMatches("File1.java", heatmap.generate());
    }

    private void givenLogContains(Commit... commits) {
        when(mockRepository.getCommits()).thenReturn(new HashSet<Commit>(Arrays.asList(commits)));
    }

    @Test
    public void writesTheReportToTheClientSpecifiedWriter() {
        givenLogContains(builder.author("tim").description("US1234 First message").addFiles("File1.java").toCommit());

        WriterSpy spy = new WriterSpy();
        new Heatmap(mockRepository, spy).generate();

        assertEquals("write(),close(),", spy.logString);
    }

    private void assertReportContains(String filename) {
        assertTrue(reportFromHg.contains(filename));
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
