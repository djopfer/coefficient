package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    @Test
    public void ordersTheTagCloudBasedOnFilename() {
        HashMap<String, Integer> changes = new HashMap<String, Integer>();
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

        assertMatches("<li .+>ChangeSet</li>", render(changes));
    }

    @Test
    public void addsFileDetailsToTheTitleAttributeForDisplayWhenHoveredOver() {
        HashMap<String, Integer> changes = new HashMap<String, Integer>();
        changes.put("src/main/java/com/example/ChangeSet.java", 1);

        assertMatches("title='src/main/java/com/example/ChangeSet.java'", render(changes));
    }

    @Test
    public void adjustsTheFontSizeOfEachTagRelativeToTheNumberOfChangesInTheFile() {
//    file_with( :changes =>  3 ).should have_font_size(6)
//    file_with( :changes =>  6 ).should have_font_size(15)
//    file_with( :changes => 12 ).should have_font_size(36)
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
//    @formatter.size_for(12).should == 36
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

    private String render(HashMap<String, Integer> changes) {
        String html = new HtmlRenderer().render(changes);
        return html;
    }
}
