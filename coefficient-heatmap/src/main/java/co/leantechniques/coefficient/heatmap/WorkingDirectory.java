package co.leantechniques.coefficient.heatmap;

import java.io.File;

public class WorkingDirectory {
    private String path;

    public WorkingDirectory(String path) {
        this.path = path;
    }

    public String getRepoDirectoryName() {
        return "hg";
    }

    public String getPath() {
        return path;
    }
}
