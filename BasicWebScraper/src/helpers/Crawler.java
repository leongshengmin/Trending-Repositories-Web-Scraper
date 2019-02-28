package helpers;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private static final int OK = 200;

    public static Document retrieveDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if (isValidContentType(connection) && isStatusOk(connection)) {
                return document;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isValidContentType(Connection connection) {
        return connection.response().contentType().contains("text/html");
    }

    private static boolean isStatusOk(Connection connection) {
        return connection.response().statusCode() == OK;
    }

}
