package co.leantechniques.coefficient.mvn.goals;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Stops the timer
 *
 * @goal log-verify
 * @phase verify
 * @requiresOnline true
 */
public class LogVerify extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
    }
}
