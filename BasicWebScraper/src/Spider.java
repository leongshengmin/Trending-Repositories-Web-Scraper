import helpers.Crawler;
import helpers.DocumentParser;
import org.jsoup.nodes.Document;

public class Spider {

    private void query(String url, String datebin) {
        Document document = Crawler.retrieveDocument(url);
        DocumentParser documentParser = ((DocumentParser) (new DocumentParser(document).parse()));
        for (String link : documentParser.getLinksToVisit()) {
            // visit link
        }

    }

    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.query("https://github.com/trending?since=daily", "");
    }
}
