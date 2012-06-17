package co.leantechniques.coefficient.heatmap;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class AdapterFactory {

    public static final HashMap<String,Class<? extends CodeRepository>> SUPPORTED_ADAPTERS = new HashMap<String, Class<? extends CodeRepository>>();

    static {
        SUPPORTED_ADAPTERS.put("hg", MercurialCodeRepository.class);
    }

    public CodeRepository adapterFor(WorkingDirectory workingDirectory)
    {
        try {
            return SUPPORTED_ADAPTERS.get(workingDirectory.getRepoDirectoryName().toLowerCase()).getDeclaredConstructor(WorkingDirectory.class).newInstance(workingDirectory);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
