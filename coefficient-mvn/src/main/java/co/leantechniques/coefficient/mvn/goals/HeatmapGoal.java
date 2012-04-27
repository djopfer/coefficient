package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.Heatmap;
import co.leantechniques.coefficient.heatmap.HgLog;
import co.leantechniques.coefficient.heatmap.ScmRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;

/**
 * Generates a heatmap
 *
 * @goal heatmap
 * @parameter expression="${project.build.directory}"
 */

public class HeatmapGoal extends AbstractMojo {
    /**
     * This is the file that the Heatmap will generate.
     *
     * @parameter expression="${project.build.directory}/coefficient/heatmap.html"
     */
    private String outputFile;

    /**
     * At this time, the plugin is expected to be configured on the POM located at
     * the root of the repository.
     *
     * @parameter expression="${basedir}"
     */
    private String scmRoot;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating heatmap in " + outputFile);

        try {
            File file = buildFileStructureFor();
            Heatmap heatmap = new Heatmap(new HgLog(scmRoot), new FileWriter(file));
            heatmap.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File buildFileStructureFor() {
        File file = new File(outputFile);
        file.getParentFile().mkdirs();
        return file;
    }
}
