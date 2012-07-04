package co.leantechniques.coefficient.heatmap;

import java.util.*;

// ChangesetAnalyzer?
public class Commitset {

    private final Iterable<Commit> commits;

    public Commitset(Iterable<Commit> commits) {
        this.commits = commits;
    }

    public AuthorStatisticSet getAuthorStatistics() {
        AuthorStatisticSet authorStatisticSet = new AuthorStatisticSet();
        for(Commit commit : commits){
            authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTotalCommits();
            authorStatisticSet.incrementTotalCommits();
            if(commit.containsTests()){
                authorStatisticSet.incrementTestedCommits();
                authorStatisticSet.getCommitStatisticForAuthor(commit.getAuthor()).incrementTestedCommits();
            }
        }
        return authorStatisticSet;
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


    //    public Map<String, Set<Commit>> commitStatisticsByAuthor(Iterable<Commit> commits) {
//        HashMap<String, Set<Commit>> commitStatisticsByAuthor = new HashMap<String, Set<Commit>>();
//        for(Commit commit : commits){
//            if (commitStatisticsByAuthor.containsKey(commit.getAuthor()))
//                commitStatisticsByAuthor.get(commit.getAuthor()).add(commit);
//            else {
//                Set<Commit> commitSet = new HashSet<Commit>();
//                commitSet.add(commit);
//                commitStatisticsByAuthor.put(commit.getAuthor(), commitSet);
//            }
//        }
//        return commitStatisticsByAuthor;
//    }



}
