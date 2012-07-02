package co.leantechniques.coefficient.heatmap;

import com.aragost.javahg.Repository;
import com.aragost.javahg.commands.LogCommand;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class MercurialCodeRepository implements CodeRepository {

    CommitLogParser logParser;
    private WorkingDirectory workingDirectory;

    // Used for reflection from AdapterFactory
    public MercurialCodeRepository(WorkingDirectory workingDirectory){
        this.workingDirectory = workingDirectory;
    }

    public MercurialCodeRepository(CommitLogParser logParser) {
        this.logParser = logParser;
    }

    @Override
    public Set<Commit> getCommits() {
        Set<Commit> commits = new HashSet<Commit>();
        while (getLogParser().hasMoreCommits()) {
            commits.add(getLogParser().nextCommit());
        }
        return commits;
    }

    private CommitLogParser getLogParser() {
        if(logParser == null) this.logParser = new CommitLogParser(getCommitLogInputStream());
        return logParser;
    }

    private InputStream getCommitLogInputStream(){
        return new HgLogs(workingDirectory.getPath()).execute();
    }

    private class HgLogs extends LogCommand {
        public HgLogs(String repoLocation) {
            super(Repository.open(new File(repoLocation)));
        }

        public InputStream execute() {

            noMerges();
            cmdAppend("--template", "{author|user}||{desc}||{files}\\n");
            return this.launchStream();
        }
//        @Override
//        public InputStream execute(Integer daystoinclude) {
//            Date now = new Date();
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DATE, -daystoinclude);
//            String logDateRange = df.format(cal.getTime()) + " to " + df.format(now);
//            date(logDateRange);
//            noMerges();
//            cmdAppend("--template", "{desc}||{files}\\n");
//            return this.launchStream();
//        }


    }
}