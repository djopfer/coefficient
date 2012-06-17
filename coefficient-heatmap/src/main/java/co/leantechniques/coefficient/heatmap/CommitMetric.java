package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommitMetric {

    public Map<String, Set<Commit>> commitsByAuthor(Iterable<Commit> commits) {
        HashMap<String, Set<Commit>> commitsByAuthor = new HashMap<String, Set<Commit>>();
        for(Commit commit : commits){
            if (commitsByAuthor.containsKey(commit.getAuthor()))
                commitsByAuthor.get(commit.getAuthor()).add(commit);
            else {
                Set<Commit> commitSet = new HashSet<Commit>();
                commitSet.add(commit);
                commitsByAuthor.put(commit.getAuthor(), commitSet);
            }
        }
        return commitsByAuthor;
    }

    public Map<String, Set<String>> filesByStory(Iterable<Commit> commits) {
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        for(Commit commit : commits){
            if (map.containsKey(commit.getStory()))
                map.get(commit.getStory()).addAll(commit.getFiles());
            else
                map.put(commit.getStory(), commit.getFiles());
        }
        return map;
    }

    public FilteredCommitSet getTestedCommitsByAuthor(Set<Commit> commits) {
        FilteredCommitSet result = new FilteredCommitSet();
        for(Commit commit : commits){
            FilteredCommitSet authorsResult = result.filterBy(commit.getAuthor());
            authorsResult.addCommit(commit);
            if(commit.containsTests())
                authorsResult.addTestedCommit(commit);
        }
        return result;
    }

    public FilteredCommitSet getTestedCommitsByStory(Set<Commit> commits) {
        FilteredCommitSet result = new FilteredCommitSet();
        for(Commit commit : commits){
            FilteredCommitSet storyResult = result.filterBy(commit.getStory()).filterBy(commit.getAuthor());
            storyResult.addCommit(commit);
            if(commit.containsTests())
                storyResult.addTestedCommit(commit);
        }
        return result;
    }

    public TestReport getReport(Set<Commit> expectedCommits) {
        TestReport report = new TestReport();
        for(Commit commit : expectedCommits){
            report.getAuthorByName(commit.getAuthor()).incrementTotalCommits();
            report.incrementTotalCommits();
            if(commit.containsTests()){ 
                report.incrementTestedCommits();
                report.getAuthorByName(commit.getAuthor()).incrementTestedCommits();
            }
        }
        return report;
    }
}
