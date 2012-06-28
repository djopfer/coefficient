package co.leantechniques.coefficient.heatmap;

public class ChangeInfo {
    private int storyChanges;

    public void changedForStory() {
        this.storyChanges++;
    }

    public int getTotalChanges() {
        return storyChanges;
    }
}
