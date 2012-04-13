package co.leantechniques.coefficient.heatmap;

import com.aragost.javahg.BaseRepository;
import com.aragost.javahg.Repository;
import com.aragost.javahg.commands.AddCommand;
import com.aragost.javahg.commands.CommitCommand;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class HeatmapTest {

    private BaseRepository repo;
    public static final File REPO_DIRECTORY = new File("./target/temp-repo");

    @Test
    public void s() {

        REPO_DIRECTORY.mkdirs();

        repo = Repository.create(REPO_DIRECTORY);
        givenCommit("First message", "File1.java", "File2.java");
        givenCommit("Second message", "File2.java", "File3.java");

        String s = new Heatmap(new HgLog("./target/temp-repo")).generateForRepository();

        assertTrue(s.contains("File1.java"));
        assertTrue(s.contains("File2.java"));
        assertTrue(s.contains("File3.java"));
    }

    private void givenCommit(String message, String... files) {
        for(String filename : files) {
            createFileNamed(filename);
        }

        AddCommand.on(repo).execute(files);
        CommitCommand.on(repo).user("Brandon").message(message).execute(files);
    }

    private void createFileNamed(String filename) {
        try {
            new File(REPO_DIRECTORY, filename).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
