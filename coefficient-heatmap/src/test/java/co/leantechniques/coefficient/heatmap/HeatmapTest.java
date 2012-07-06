package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeatmapTest {

    private String reportFromHg;
    private Heatmap heatmap;
    private CodeRepository mockRepository;

    @Before
    public void setUp() throws Exception {
        mockRepository = mock(CodeRepository.class);
        heatmap = new Heatmap(mockRepository, new NullWriter());
    }

    @Test
    public void supportsCommitMessagesWithEmbeddedNewlines() {
        String descriptionWithNewLines = "US1234 Message with" + System.getProperty("line.separator") + "embedded newline";
        givenLogContains(new Commit("tim", descriptionWithNewLines, "File1.java", "File2.java"));

        reportFromHg = heatmap.generate();

        assertThat(reportFromHg, containsString("File1.java"));
        assertThat(reportFromHg, containsString("File2.java"));
    }

    @Test
    public void reportsOnEachFileCommitted() {
        givenLogContains(
                new Commit("tim", "US1234 First message", "File1.java", "File1.java"),
                new Commit("tim", "", "File2.java", "File3.java"));

//                builder.author("tim").description("US1234 First message").addFiles("File1.java", "File2.java").toCommit(),
//                builder.author("tim").description("").addFiles("File2.java", "File3.java").toCommit());

        reportFromHg = heatmap.generate();

        assertThat(reportFromHg, containsString("File1.java"));
        assertThat(reportFromHg, containsString("File2.java"));
        assertThat(reportFromHg, containsString("File3.java"));
    }

    @Test
    public void multipleCommitsForTheSameTicketAreTreatedAsSingleChange() {
        givenLogContains(
                new Commit("tim", "US1234 First message", "File1.java"),
                new Commit("tim", "US1234 Second message", "File1.java"));

        reportFromHg = heatmap.generate();

        assertThat(reportFromHg, containsString("File1.java -> Changes: 1  Defects: 0"));
    }

    private void givenLogContains(Commit... commits) {
        when(mockRepository.getCommits()).thenReturn(new HashSet<Commit>(Arrays.asList(commits)));
    }

    @Test
    public void writesTheReportToTheClientSpecifiedWriter() {
        givenLogContains(new Commit("tim", "US1234 First message", "File1.java"));

        WriterSpy spy = new WriterSpy();
        new Heatmap(mockRepository, spy).generate();

        assertEquals("write(),close(),", spy.logString);
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
