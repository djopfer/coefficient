package co.leantechniques.coefficient.heatmap;

import java.util.Set;

public interface CodeRepository {
    //TODO: Could this return a CommitSet?
    Set<Commit> getCommits();
}
