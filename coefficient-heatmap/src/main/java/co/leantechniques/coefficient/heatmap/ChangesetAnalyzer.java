package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class ChangesetAnalyzer {
    private final CodeRepository codeRepository;

    public ChangesetAnalyzer(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Map<String, Set<String>> getFilesByStory() {
        Set<Commit> commits = codeRepository.getCommits();
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        for(Commit commit : commits){
            if (map.containsKey(commit.getStory()))
                map.get(commit.getStory()).addAll(commit.getFiles());
            else
                map.put(commit.getStory(), commit.getFiles());
        }
        return map;
    }

    public AuthorStatisticSet getAuthorStatistics() {
        Set<Commit> commits = codeRepository.getCommits();
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