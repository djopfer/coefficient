package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WorkingDirectoryTest {

    @Test
    public void getRepoDirectoryName(){
        WorkingDirectory workingDirectory = new WorkingDirectory("C:\\SomeDirectory\\");

        assertThat(workingDirectory.getRepoDirectoryName(), equalTo("hg"));
    }
}
