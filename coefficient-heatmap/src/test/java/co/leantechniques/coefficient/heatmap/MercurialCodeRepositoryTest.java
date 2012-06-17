package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class MercurialCodeRepositoryTest {

    private CommitLogParser parserMock;

    @Test
    public void usesParserToCreateCommits(){
        parserMock = mock(CommitLogParser.class);
        MercurialCodeRepository codeRepository = new MercurialCodeRepository(parserMock);

        when(parserMock.hasMoreCommits())
                .thenReturn(true)
                .thenReturn(false);

        codeRepository.getCommits();

        verify(parserMock, times(1)).nextCommit();
    }
}
