package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CommitTest {
    @Test
    public void knowsWhatStoryItsFor() {
        assertEquals("US1234", new Commit(null, "US1234 Some message").getStory());
    }

    @Test
    public void admitsItWhenItCantFigureOutWhatStory() {
        assertEquals("Unknown", new Commit(null, "Message without a story").getStory());
    }

    @Test
    public void knowsWhatFilesAreContainedWithinTheCommit() {
        Set<String> filesCommitted = new Commit(null, null, "File1.java", "File2.java", "File3.java").getFiles();

        assertEquals(3, filesCommitted.size());

        assertTrue(filesCommitted.contains("File1.java"));
        assertTrue(filesCommitted.contains("File2.java"));
        assertTrue(filesCommitted.contains("File3.java"));
    }
    
    @Test
    public void knowsWhenCommitContainsATest(){
        Commit commitWithTest = new Commit(null, null, "File1.java", "File1Test.java");
        
        assertTrue(commitWithTest.containsTests());
    }

    @Test
    public void knowsWhenCommitNotTested(){
        Commit commitWithTest = new Commit(null, null, "File1.java");

        assertFalse(commitWithTest.containsTests());
    }

    @Test
    public void getStoryHandlesNulls(){
        Commit commitWithTest = new Commit(null, null, "File1.java");

        assertThat(commitWithTest.getStory(), notNullValue());
    }
}
