import java.util.HashSet;
import java.util.LinkedList;

import helpers.Crawler;
import helpers.DocumentParser;
import org.jsoup.nodes.Document;

public class Spider {

    private HashSet<String> visitedUrls;
    private LinkedList<String> linksToVisit;
    private String rootUrl;

    Spider(String rootUrl) {
        this.rootUrl = rootUrl;
        this.visitedUrls = new HashSet<>();
    }

    public void query() {
        query(rootUrl);
    }

    private void query(String link) {
        visitedUrls.add(link);
        Document document = Crawler.retrieveDocument(link);
        DocumentParser documentParser = ((DocumentParser) (new DocumentParser(document).parse()));
        System.out.println(documentParser);  // print parsed contents
        for (String toVisit : documentParser.getLinksToVisit()) {
            if (!visitedUrls.contains(toVisit)) {
                query(toVisit);
            }
        }
    }

    public static void main(String[] args) {
        String rootUrl = "https://github.com/trending?since=daily";
        Spider spider = new Spider(rootUrl);
        spider.query();
    }
}
