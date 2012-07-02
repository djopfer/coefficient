package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    private Map<String, HeatmapData> changes;

    @Before
    public void setUp() {
        changes = new TreeMap<String, HeatmapData>();
    }

    @Test
    public void generatesTheCloudInHtml() {
        initializeChanges("AbstractChainOfResponsibilityFactory.java", 1, 0);
        assertMatches("<html><head>(.*)</head><body><ol>(.*)</ol></body></html>", render(changes));
    }

    private void initializeChanges(String filename, int totalNumberOfChanges, int totalNumberOfDefects) {
        HeatmapData heatmapData = new HeatmapData();
        heatmapData.changes = totalNumberOfChanges;
        heatmapData.defects = totalNumberOfDefects;
        changes.put(filename, heatmapData);
    }

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        initializeChanges("AbstractChainOfResponsibilityFactory.java", 1, 0);
        initializeChanges("ZumbaTraining.java", 1, 0);
        initializeChanges("BasicChainOfResponsibilityFactory.java", 1, 0);

        assertMatches(".*AbstractChainOfResponsibilityFactory.*BasicChainOfResponsibilityFactory.*ZumbaTraining.*", render(changes));
    }

//    private void numberOfChangesFor(String filename, int numberOfChanges) {
//        ChangeInfo changeInfo = new ChangeInfo();
//        for(int i = 0; i < numberOfChanges; i++) {
//            changeInfo.changedForStory();
//        }
//        changes.put(filename, changeInfo);
//    }

    @Test
    public void rendersEachFileAsATag() {
        initializeChanges("ChangeSet.java", 1, 0);

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void onlyShowsBaseFilenameForEachTag() {
        initializeChanges("src/main/java/com/example/ChangeSet.java", 1, 0);

        assertMatches("<li(.+)>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        initializeChanges("src/main/java/com/example/ChangeSet.java", 1, 0);

        assertMatches("title='src/main/java/com/example/ChangeSet.java -> Changes: 1  Defects: 0'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
        initializeChanges("src/main/java/com/example/NotChangedOften.java", 1, 0);
        initializeChanges("src/main/java/com/example/ChangedMoreOften.java", 6, 0);
        initializeChanges("src/main/java/com/example/AlwaysChanging.java", 12, 0);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 18", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheFontSizeRelativeToTheTotalNumberOfChanges() {
        initializeChanges("src/main/java/com/example/NotChangedOften.java", 10, 0);
        initializeChanges("src/main/java/com/example/ChangedMoreOften.java", 15, 0);
        initializeChanges("src/main/java/com/example/AlwaysChanging.java", 20, 0);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 21", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheColorOfEachTagBasedOnTheNumberOfChangesThatWereDefects() {
        initializeChanges("src/main/java/com/example/NoDefects.java", 10, 0);
        initializeChanges("src/main/java/com/example/MinimalDefects.java", 15, 3);
        initializeChanges("src/main/java/com/example/ManyDefects.java", 20, 15);

        String html = render(changes);

        assertMatches("211,211,211", html);
        assertMatches("51,0,0", html);
        assertMatches("191,0,0", html);
    }

    private void assertMatches(String pattern, String target) {
        assertTrue(assertionMessage(pattern, target), target.matches(".*" + pattern + ".*"));
    }

    private String assertionMessage(String pattern, String target) {
        return "Expected <" + target + "> to match <" + pattern + ">";
    }

    private String render(Map<String, HeatmapData> changes) {
        return new HtmlRenderer(changes).render();
    }
}
