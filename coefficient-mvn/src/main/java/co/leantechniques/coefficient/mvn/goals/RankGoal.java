package co.leantechniques.coefficient.mvn.goals;

import co.leantechniques.coefficient.heatmap.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Generates a report of the top authors of commits with tests
 * > mvn coefficient-mvn:rank
 *
 * @goal rank
 * @parameter expression="${project.build.directory}"
 */

public class RankGoal extends AbstractMojo{

    private AdapterFactory factory = new AdapterFactory();

    /**
     * At this time, the plugin is expected to be configured on the POM located at
     * the root of the repository.
     *
     * @parameter expression="${basedir}"
     */
    private String scmRoot;
    private ChangesetAnalyzer changesetAnalyzer;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        CommitsetStatistic testedCommitsByAuthor = getChangesetAnalyzer().getRankReport();
        getLog().info(String.format("%.2f%% of the %d commits contain test files", testedCommitsByAuthor.getPercentageOfTestedCommits()*100, testedCommitsByAuthor.getTotalCommits()));

        for(AuthorCommitStatistic authorCommits : testedCommitsByAuthor.getCommitStatistics()){
            getLog().info(String.format("%.0f%%\t%s\t\t\t(%d of %d commits)",
                    authorCommits.getPercentageOfTestedCommits() * 100,
                    authorCommits.getAuthor(),
                    authorCommits.getCountOfTestedCommits(),
                    authorCommits.getCountOfCommits()));
        }
    }

    private ChangesetAnalyzer getChangesetAnalyzer() {
        if(changesetAnalyzer == null) changesetAnalyzer = new ChangesetAnalyzer(factory.adapterFor(new WorkingDirectory(scmRoot)));
        return changesetAnalyzer;
    }

    public void setChangesetAnalyzer(ChangesetAnalyzer changesetAnalyzer) {
        this.changesetAnalyzer = changesetAnalyzer;
    }

}
