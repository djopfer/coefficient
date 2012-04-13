package co.leantechniques.coefficient.mvn.goals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlHandler {
    public boolean makeRequestTo(String url) {
        try {
            return get(url).getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    private HttpURLConnection get(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }
}
