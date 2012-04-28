package co.leantechniques.coefficient.heatmap;

import co.leantechniques.coefficient.heatmap.AdapterFactory;
import co.leantechniques.coefficient.heatmap.HgLog;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AdapterFactoryTest {
    @Test
    public void supportsMercurial() {
        assertTrue(new AdapterFactory().adapterFor("hg") instanceof HgLog);
    }
}
