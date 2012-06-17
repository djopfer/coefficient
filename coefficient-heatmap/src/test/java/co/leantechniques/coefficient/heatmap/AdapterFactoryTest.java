package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdapterFactoryTest {

    @Test
    public void determineBasedOnRepositoryLocation(){
        WorkingDirectory hgWorking = mock(WorkingDirectory.class);
        when(hgWorking.getRepoDirectoryName()).thenReturn("hg");

        CodeRepository actual = new AdapterFactory().adapterFor(hgWorking);

        assertThat(actual, instanceOf(MercurialCodeRepository.class));
    }
}

