package co.leantechniques.coefficient.mvn.goals;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LogVerifyTest {

    private Log logger;
    private LogVerify goal;
    private FakeUrlHandler fakeHandler;

    @Before
    public void setUp() throws Exception {
        goal = new MockedLogVerify();
        logger = mock(Log.class);
        fakeHandler = configureHandler();
    }

    @Test
    public void usesTheUrlSpecifiedToMakeTheRequest() throws MojoExecutionException, MojoFailureException {
        whenUrlIsConfiguredAs("http://someserver/logger");

        goal.execute();

        assertEquals("http://someserver/logger", fakeHandler.calledWith);
    }

    @Test
    public void allowsParameterizedUrlsThatContainPlaceholderForUsername() throws MojoExecutionException, MojoFailureException {
        assumingLoggedInUserIs("bob");
        whenUrlIsConfiguredAs("http://someserver/logger?username=${username}");

        goal.execute();

        assertEquals("http://someserver/logger?username=bob", fakeHandler.calledWith);
    }

    @Test
    public void isSilentWhenRequestSucceeds() throws MojoExecutionException, MojoFailureException {
        goal.setUrlHandler(new SuccessfulUrlHandler());

        goal.execute();

        verifyZeroInteractions(logger);
    }

    @Test
    public void warnsWhenRequestFails() throws MojoExecutionException, MojoFailureException {
        goal.setUrlHandler(new FailingUrlHandler());

        goal.execute();

        verify(logger).warn("Unable to access the server for logging 'verify'. URL used is ''.");
    }

    private FakeUrlHandler configureHandler() {
        FakeUrlHandler fakeHandler = new FakeUrlHandler();
        goal.setUrlHandler(fakeHandler);
        return fakeHandler;
    }

    private void whenUrlIsConfiguredAs(String url) {
        goal.setUrl(url);
    }

    private String assumingLoggedInUserIs(String username) {
        return System.setProperty("user.name", username);
    }

    private class MockedLogVerify extends LogVerify {
        @Override
        public Log getLog() {
            return logger;
        }
    }

    private class FakeUrlHandler extends UrlHandler {
        public String calledWith;

        @Override
        public boolean makeRequestTo(String url) {
            calledWith = url;
            return true;
        }
    }

    private class FailingUrlHandler extends UrlHandler {
        @Override
        public boolean makeRequestTo(String url) {
            return false;
        }
    }

    private class SuccessfulUrlHandler extends UrlHandler {
        @Override
        public boolean makeRequestTo(String url) {
            return true;
        }
    }
}
