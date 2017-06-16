package pages;

/**
 * Created by Serguei on 6/15/2017.
 */
public abstract class Page {

    public static String getUrl() {
        throw new RuntimeException("getUrl has not been overridden.");
    }

}
