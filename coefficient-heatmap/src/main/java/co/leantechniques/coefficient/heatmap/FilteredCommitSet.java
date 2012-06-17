package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class FilteredCommitSet {
    private Set<Commit> allCommits = new HashSet<Commit>();
    private Set<Commit> allTestedCommits = new HashSet<Commit>();
    private Set<String> allAuthors = new HashSet<String>();
    private Map<String, FilteredCommitSet> testedCommitResults = new HashMap<String, FilteredCommitSet>();

    public FilteredCommitSet filterBy(String key) {
        if(!testedCommitResults.containsKey(key)) testedCommitResults.put(key, new FilteredCommitSet());
        return testedCommitResults.get(key);
    }

    public Set<Commit> getAllCommits() {
        for(FilteredCommitSet set : testedCommitResults.values()){
           allCommits.addAll(set.getAllCommits());
        }
        return Collections.unmodifiableSet(allCommits);
    }

    public Set<Commit> getTestedCommits() {
        for(FilteredCommitSet set : testedCommitResults.values()){
            allTestedCommits.addAll(set.getTestedCommits());
        }
        return Collections.unmodifiableSet(allTestedCommits);
    }

    public void addCommit(Commit commit) {
        allCommits.add(commit);
    }

    public void addTestedCommit(Commit commit) {
        allTestedCommits.add(commit);
    }

    public Set<String> getAllAuthors() {
        return allAuthors;
    }
}
