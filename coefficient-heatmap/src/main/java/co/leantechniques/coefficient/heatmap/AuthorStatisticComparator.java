package co.leantechniques.coefficient.heatmap;

import java.util.Comparator;

public class AuthorStatisticComparator implements Comparator<AuthorStatistic> {
    @Override
    public int compare(AuthorStatistic o1, AuthorStatistic o2) {
        return (o1.getPercentageOfTestedCommits() > o2.getPercentageOfTestedCommits() ? -1 : (o1.getPercentageOfTestedCommits() == o2.getPercentageOfTestedCommits() ? 0 : 1));
    }
}
