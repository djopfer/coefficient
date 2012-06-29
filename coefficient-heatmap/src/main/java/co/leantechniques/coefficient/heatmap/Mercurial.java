package co.leantechniques.coefficient.heatmap;

import com.aragost.javahg.Repository;
import com.aragost.javahg.commands.LogCommand;

import java.io.File;
import java.io.InputStream;

public class Mercurial implements ScmAdapter {

    private String repoLocation;

    public Mercurial() {
        this.repoLocation = repoLocation;
    }

    public void setRepoLocation(String repoLocation) {
        this.repoLocation = repoLocation;
    }

    public InputStream execute() {
        return new HgLogs(repoLocation).execute();
    }

    private class HgLogs extends LogCommand {
        public HgLogs(String repoLocation) {
            super(Repository.open(new File(repoLocation)));
        }

        public InputStream execute() {
            cmdAppend("--template", "{author|user}||{desc}||{files}\\n");
            return this.launchStream();
        }
    }
}
