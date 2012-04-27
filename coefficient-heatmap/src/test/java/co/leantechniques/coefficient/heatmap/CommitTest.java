package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CommitTest {
    @Test
    public void knowsWhatStoryItsFor() {
        assertEquals("US1234", new Commit("US1234 Some message").getStory());
    }

    @Test
    public void admitsItWhenItCantFigureOutWhatStory() {
        assertEquals("Unknown", new Commit("Message without a story").getStory());
    }

    @Test
    public void knowsWhatFilesAreContainedWithinTheCommit() {
        Set<String> filesCommitted = new Commit(null, "File1.java", "File2.java", "File3.java").getFiles();

        assertEquals(3, filesCommitted.size());

        assertTrue(filesCommitted.contains("File1.java"));
        assertTrue(filesCommitted.contains("File2.java"));
        assertTrue(filesCommitted.contains("File3.java"));
    }
}
