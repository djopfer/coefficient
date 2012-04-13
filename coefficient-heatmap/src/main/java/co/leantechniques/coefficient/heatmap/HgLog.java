package co.leantechniques.coefficient.heatmap;

import com.aragost.javahg.Repository;
import com.aragost.javahg.commands.LogCommand;
import com.aragost.javahg.internals.LineIterator;

import java.io.File;

public class HgLog extends LogCommand {

    public HgLog(String repoLocation) {
        super(Repository.open(new File(repoLocation)));
    }

    public LineIterator execute() {
        cmdAppend("--template", "{desc}||{files}\\n");
        return launchIterator();
    }
}
