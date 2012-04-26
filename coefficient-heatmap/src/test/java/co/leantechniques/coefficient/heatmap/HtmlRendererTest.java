package co.leantechniques.coefficient.heatmap;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        Map<String, Integer> changes = new TreeMap<String, Integer>();
        changes.put("AbstractChainOfResponsibilityFactory.java", 1);
        changes.put("ZumbaTraining.java", 1);
        changes.put("BasicChainOfResponsibilityFactory.java", 1);

        assertMatches(".*AbstractChainOfResponsibilityFactory.*BasicChainOfResponsibilityFactory.*ZumbaTraining.*", render(changes));
    }

    @Test
    public void rendersEachFileAsATag() {
        HashMap<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("ChangeSet.java", 1);

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void onlyShowsBaseFilenameForEachTag() {
        HashMap<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("<li(.+)>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        HashMap<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("title='src/main/java/com/example/ChangeSet.java'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
        Map<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("src/main/java/com/example/NotChangedOften.java", 1);
        changes.put("src/main/java/com/example/ChangedMoreOften.java", 6);
        changes.put("src/main/java/com/example/AlwaysChanging.java", 12);

        String html = render(changes);

        assertMatches("font-size: 6", html);
        assertMatches("font-size: 18", html);
        assertMatches("font-size: 36", html);
    }
    @Test
    public void adjustsTheFontSizeRelativeToTheTotalNumberOfChanges() {
        Map<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("src/main/java/com/example/NotChangedOften.java", 10);
        changes.put("src/main/java/com/example/ChangedMoreOften.java", 15);
        changes.put("src/main/java/com/example/AlwaysChanging.java", 20);

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

    @Test
    public void generatesARangeOfFontSizesBasedOnAMinMax() {
//            @formatter.size_for(12).should == 36
//    @formatter.size_for(11).should == 30
//    @formatter.size_for(8).should  == 21
//    @formatter.size_for(7).should  == 18
//    @formatter.size_for(4).should  ==  9
//    @formatter.size_for(3).should  ==  6

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
