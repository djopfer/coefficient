package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    private Map<String, ChangeInfo> changes;

    @Before
    public void setUp() {
        changes = new TreeMap<String, ChangeInfo>();
    }


    @Test
    public void generatesTheCloudInHtml() {
        String filename = "AbstractChainOfResponsibilityFactory.java";
        int numberOfChanges = 1;
        numberOfChangesFor2(filename, numberOfChanges);
        assertMatches("<html><head>(.*)</head><body><ol>(.*)</ol></body></html>", render(changes));
    }

    private void numberOfChangesFor2(String filename, int numberOfChanges) {
        numberOfChangesFor(filename, numberOfChanges);
    }

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        numberOfChangesFor2("AbstractChainOfResponsibilityFactory.java", 1);
        numberOfChangesFor2("ZumbaTraining.java", 1);
        numberOfChangesFor2("BasicChainOfResponsibilityFactory.java", 1);

        assertMatches(".*AbstractChainOfResponsibilityFactory.*BasicChainOfResponsibilityFactory.*ZumbaTraining.*", render(changes));
    }

    private void numberOfChangesFor(String filename, int numberOfChanges) {
        ChangeInfo changeInfo = new ChangeInfo();
        for(int i = 0; i < numberOfChanges; i++) {
            changeInfo.changedForStory();
        }
        changes.put(filename, changeInfo);
    }

    @Test
    public void rendersEachFileAsATag() {
        numberOfChangesFor2("ChangeSet.java", 1);

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void onlyShowsBaseFilenameForEachTag() {
        numberOfChangesFor2("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("<li(.+)>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        numberOfChangesFor2("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("title='src/main/java/com/example/ChangeSet.java'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
        numberOfChangesFor2("src/main/java/com/example/NotChangedOften.java", 1);
        numberOfChangesFor2("src/main/java/com/example/ChangedMoreOften.java", 6);
        numberOfChangesFor2("src/main/java/com/example/AlwaysChanging.java", 12);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 18", html);
        assertMatches("font-size: 36", html);
    }

    @Test
    public void adjustsTheFontSizeRelativeToTheTotalNumberOfChanges() {
        numberOfChangesFor2("src/main/java/com/example/NotChangedOften.java", 10);
        numberOfChangesFor2("src/main/java/com/example/ChangedMoreOften.java", 15);
        numberOfChangesFor2("src/main/java/com/example/AlwaysChanging.java", 20);

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

    private String render(Map<String, ChangeInfo> changes) {
        return new HtmlRenderer(changes).render();
    }
}
