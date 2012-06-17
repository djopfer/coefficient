package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class CommitInfoBuilder {
    private String author;
    private String description;
    private final HashSet<String> files = new HashSet<String>();


    public static CommitInfoBuilder create() {
        return new CommitInfoBuilder();
    }

    public CommitInfoBuilder(){
        clear();
    }

    private void clear() {
         author = "";
         description = "" ;
         files.clear();
    }

    public CommitInfoBuilder author(String author) {
        this.author = author;
        return this;
    }

    public CommitInfoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Commit toCommit() {
        Commit commit = new Commit(this.author, this.description);
        commit.getFiles().addAll(files);
        clear();
        return commit;
    }

    public CommitInfoBuilder addFiles(String... files) {
        this.files.addAll(Arrays.asList(files));
        return this;
    }
}
