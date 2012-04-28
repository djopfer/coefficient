package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;

public class AdapterFactory {

    public static final HashMap<String,Class<? extends ScmAdapter>> SUPPORTED_ADAPTERS = new HashMap<String, Class<? extends ScmAdapter>>();

    static {
        SUPPORTED_ADAPTERS.put("hg", Mercurial.class);
    }

    public ScmAdapter adapterFor(String scmAdapter) {
        try {
            return SUPPORTED_ADAPTERS.get(scmAdapter.toLowerCase()).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
