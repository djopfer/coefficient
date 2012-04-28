package co.leantechniques.coefficient.heatmap;

import java.io.InputStream;

public interface ScmAdapter {
    InputStream execute();

    void setRepoLocation(String scmRoot);
}
