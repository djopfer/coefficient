package co.leantechniques.coefficient.heatmap;

public class AuthorCommitStatistic {

    String author;
    int countOfTestedCommits;
    int countOfCommits;

    public AuthorCommitStatistic(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
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
