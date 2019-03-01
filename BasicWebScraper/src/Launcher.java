import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.Crawler;
import helpers.Parser;
import org.jsoup.nodes.Document;

public class Launcher {

    private void query(String url, String datebin) {
        Document document = Crawler.retrieveDocument(url);
        HashMap<String, List<String>> data = Parser.parse(document);

    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.query("https://github.com/trending?since=daily", "");
    }
}
