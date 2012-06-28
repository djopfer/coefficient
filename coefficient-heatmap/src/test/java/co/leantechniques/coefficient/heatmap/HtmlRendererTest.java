package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    private Map<String, Integer> changes;
    private TreeMap<String, ChangeInfo> newchanges;

    @Before
    public void setUp() {
        changes = new TreeMap<String, Integer>();
        newchanges = new TreeMap<String, ChangeInfo>();
    }


    @Test
    public void generatesTheCloudInHtml() {
        numberOfChangesFor("AbstractChainOfResponsibilityFactory.java", 1);
        assertMatches("<html><head>(.*)</head><body><ol>(.*)</ol></body></html>", render(changes));
    }

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        numberOfChangesFor("AbstractChainOfResponsibilityFactory.java", 1);
        numberOfChangesFor("ZumbaTraining.java", 1);
        numberOfChangesFor("BasicChainOfResponsibilityFactory.java", 1);

        assertMatches(".*AbstractChainOfResponsibilityFactory.*BasicChainOfResponsibilityFactory.*ZumbaTraining.*", render(changes));
    }

    private void numberOfChangesFor(String filename, int numberOfChanges) {
        changes.put(filename, numberOfChanges);
        ChangeInfo changeInfo = new ChangeInfo();
        for(int i = 0; i < numberOfChanges; i++) {
            changeInfo.changedForStory();
        }
        newchanges.put(filename, changeInfo);
    }

    @Test
    public void rendersEachFileAsATag() {
        numberOfChangesFor("ChangeSet.java", 1);

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void onlyShowsBaseFilenameForEachTag() {
        numberOfChangesFor("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("<li(.+)>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        numberOfChangesFor("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("title='src/main/java/com/example/ChangeSet.java'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
        numberOfChangesFor("src/main/java/com/example/NotChangedOften.java", 1);
        numberOfChangesFor("src/main/java/com/example/ChangedMoreOften.java", 6);
        numberOfChangesFor("src/main/java/com/example/AlwaysChanging.java", 12);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 18", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheFontSizeRelativeToTheTotalNumberOfChanges() {
        numberOfChangesFor("src/main/java/com/example/NotChangedOften.java", 10);
        numberOfChangesFor("src/main/java/com/example/ChangedMoreOften.java", 15);
        numberOfChangesFor("src/main/java/com/example/AlwaysChanging.java", 20);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 21", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheFontColorOfEachTagBasedOnTheNumberOfChangesThatWereDefects() {
//    file_with( :changes => 3 ).should have_color('0, 255, 0')
//
//    file_with( :changes => 3,
//    :defects => 3 ).should have_color('255, 0, 0')
//
//    file_with( :changes => 10,
//    :defects => 5 ).should have_color('127, 128, 0')
    }

    private void assertMatches(String pattern, String target) {
        assertTrue(assertionMessage(pattern, target), target.matches(".*" + pattern + ".*"));
    }

    private String assertionMessage(String pattern, String target) {
        return "Expected <" + target + "> to match <" + pattern + ">";
    }

    private String render(Map<String, Integer> changes) {
        return new HtmlRenderer(changes).render();
    }
}
