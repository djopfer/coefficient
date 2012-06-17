package co.leantechniques.coefficient.heatmap;

public class AuthorCommitInfo {

    String name;
    int countOfTestedCommits;
    int countOfCommits;

    public AuthorCommitInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCountOfTestedCommits() {
        return countOfTestedCommits;
    }

    public int getCountOfCommits() {
        return countOfCommits;
    }

    public double getPercentageOfTestedCommits() {
        return (double)countOfTestedCommits/countOfCommits;
    }

    public void incrementTotalCommits() {
        incrementTotalCommitsBy(1);
    }

    public void incrementTestedCommits() {
        countOfTestedCommits++;
    }

    public void incrementTotalCommitsBy(int i) {
        countOfCommits += i;
    }

    public void setCountOfCommits(int countOfCommits) {
        this.countOfCommits = countOfCommits;
    }

    public void setTestedCommits(int testedCommits) {
        this.countOfTestedCommits = testedCommits;
    }
}
