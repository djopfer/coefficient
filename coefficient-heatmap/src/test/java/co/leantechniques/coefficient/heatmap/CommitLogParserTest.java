package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommitLogParserTest {

    private CommitLogParser logParser;
    private InputStream logInputStream = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };

    @Test
    public void emptyLogNoMoreCommits(){
        givenLogContains("");

        assertThat(logParser.hasMoreCommits(), is(false));
    }

    @Test
    public void hasMoreCommits(){
        givenLogContains("tim||US1234 some message||File1.java");

        assertThat(logParser.hasMoreCommits(), is(true));
    }

    @Test
    public void hasNoMoreCommitsAfterGettingTheLastCommit(){
        givenLogContains("tim||US1234 some message||File1.java");

        logParser.nextCommit();

        assertThat(logParser.hasMoreCommits(), is(false));
    }

    @Test
    public void createFrom(){
        givenLogContains("tim||US1234 some message||File1.java");

        Commit actual = logParser.nextCommit();

        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles().size(), is(1));
    }

    @Test
    public void createWhenDescriptionContainsNewLines(){
        givenLogContains("tim||US1234 Message with" + System.getProperty("line.separator") + "embedded newline||File1.java");

        Commit actual = logParser.nextCommit();
        
        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles().size(), is(1));
    }

    @Test
    public void createWithoutStory(){
        givenLogContains("tim||Message without story||File1.java");

        Commit actual = logParser.nextCommit();

        assertThat(actual.getStory(), is("Unknown"));
    }

    @Test
    public void createForMergeCommitWithoutFiles(){
        givenLogContains("tim||US1234 Message without files||");

        Commit actual = logParser.nextCommit();

        assertThat(actual.getAuthor(), is("tim"));
        assertThat(actual.getStory(), is("US1234"));
        assertThat(actual.getFiles().size(), is(0));
    }

    private void givenLogContains(String... commits) {
        String commitData = "";
        for (String commit : commits) {
            commitData += commit;
        }

        logInputStream = new ByteArrayInputStream(commitData.getBytes());
        logParser = new CommitLogParser(logInputStream);
    }
}
