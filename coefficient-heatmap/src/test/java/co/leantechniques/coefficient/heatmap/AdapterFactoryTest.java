package co.leantechniques.coefficient.heatmap;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AdapterFactoryTest {
    @Test
    public void supportsMercurial() {
        assertTrue(new AdapterFactory().adapterFor("hg") instanceof Mercurial);
    }
}

