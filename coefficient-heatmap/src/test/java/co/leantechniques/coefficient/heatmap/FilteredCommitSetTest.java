package co.leantechniques.coefficient.heatmap;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class FilteredCommitSetTest {

    private FilteredCommitSet filteredCommits;

    @Before
    public void setUp() throws Exception {
        filteredCommits = new FilteredCommitSet();
    }

    @Test
    public void filterByNeverReturnsNull(){
        FilteredCommitSet actual = filteredCommits.filterBy("not there");

        assertThat(actual, notNullValue());
    }

    @Test
    public void filterByReturnsSameInstanceWhenCalledMultipleTimes(){
        FilteredCommitSet expected = filteredCommits.filterBy("one");

        FilteredCommitSet actual = filteredCommits.filterBy("one");

        assertThat(actual, sameInstance(expected));
    }

    @Test
    public void getAllCommitIsEmptyInitially(){
        assertThat(filteredCommits.getAllCommits().size(), equalTo(0));
    }

    @Test
    public void getAllCommitsIncludesAllFilteredCommits(){
        filteredCommits.filterBy("one").addCommit(new Commit("one", "message"));
        filteredCommits.filterBy("two").addCommit(new Commit("two", "message"));

        assertThat(filteredCommits.getAllCommits().size(), equalTo(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void commitsAreReadonly(){
        filteredCommits.getAllCommits().add(new Commit("","",""));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testedCommitsAreReadonly(){
        filteredCommits.getTestedCommits().add(new Commit("","",""));
    }

    
}
