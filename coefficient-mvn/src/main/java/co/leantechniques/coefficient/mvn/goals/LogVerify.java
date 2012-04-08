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
    /**
     * The URL that records the verify event.
     *
     * @parameter url=""
     */
    private String url;
    private UrlHandler urlHandler;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!urlHandler.makeRequestTo(url))
            getLog().warn("Unable to access the server for logging the verify command.");
    }

    public void setUrlHandler(UrlHandler urlHandler) {
        this.urlHandler = urlHandler;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}