package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class Commitset {

    private Iterable<Commit> commits;
    private final CommitsetStatistic commitsetStatistic = new CommitsetStatistic();

    public Commitset(Iterable<Commit> commits) {
        this.commits = commits;
    }


    public int getTotalCommits() {
        return commitsetStatistic.getTotalCommits();
    }

    public int getTotalTestedCommits() {
        return commitsetStatistic.getTotalTestedCommits();
    }

    public List<AuthorCommitStatistic> getCommitStatistics() {
        return commitsetStatistic.getCommitStatistics();
    }

    public void incrementTotalCommits() {
        commitsetStatistic.incrementTotalCommits();
    }

    public void incrementTestedCommits() {
        commitsetStatistic.incrementTestedCommits();
    }

    public AuthorCommitStatistic getAuthorByName(String author) {
        return commitsetStatistic.getAuthorByName(author);
    }

    public void setTotalCommits(int totalCommits) {
        commitsetStatistic.setTotalCommits(totalCommits);
    }

    public double getPercentageOfTestedCommits() {
        return commitsetStatistic.getPercentageOfTestedCommits();
    }





    public CommitsetStatistic getReport() {
        for(Commit commit : commits){
            commitsetStatistic.getAuthorByName(commit.getAuthor()).incrementTotalCommits();
            commitsetStatistic.incrementTotalCommits();
            if(commit.containsTests()){
                commitsetStatistic.incrementTestedCommits();
                commitsetStatistic.getAuthorByName(commit.getAuthor()).incrementTestedCommits();
            }
        }
        return commitsetStatistic;
    }

    public Map<String, Set<String>> filesByStory() {
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        for(Commit commit : commits){
            if (map.containsKey(commit.getStory()))
                map.get(commit.getStory()).addAll(commit.getFiles());
            else
                map.put(commit.getStory(), commit.getFiles());
        }
        return map;
    }

    public FilteredCommitSet getTestedCommitsByAuthor() {
        FilteredCommitSet result = new FilteredCommitSet();
        for(Commit commit : commits){
            FilteredCommitSet authorsResult = result.filterBy(commit.getAuthor());
            authorsResult.addCommit(commit);
            if(commit.containsTests())
                authorsResult.addTestedCommit(commit);
        }
        return result;
    }

    public FilteredCommitSet getTestedCommitsByStory() {
        FilteredCommitSet result = new FilteredCommitSet();
        for(Commit commit : commits){
            FilteredCommitSet storyResult = result.filterBy(commit.getStory()).filterBy(commit.getAuthor());
            storyResult.addCommit(commit);
            if(commit.containsTests())
                storyResult.addTestedCommit(commit);
        }
        return result;
    }
    //    public Map<String, Set<Commit>> commitsByAuthor(Iterable<Commit> commits) {
//        HashMap<String, Set<Commit>> commitsByAuthor = new HashMap<String, Set<Commit>>();
//        for(Commit commit : commits){
//            if (commitsByAuthor.containsKey(commit.getAuthor()))
//                commitsByAuthor.get(commit.getAuthor()).add(commit);
//            else {
//                Set<Commit> commitSet = new HashSet<Commit>();
//                commitSet.add(commit);
//                commitsByAuthor.put(commit.getAuthor(), commitSet);
//            }
//        }
//        return commitsByAuthor;
//    }



}
