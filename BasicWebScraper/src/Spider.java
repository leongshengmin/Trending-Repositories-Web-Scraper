import java.util.HashSet;

import helpers.Crawler;
import helpers.DocumentParser;
import org.jsoup.nodes.Document;

public class Spider {
    private static final String ROOT_URL ="https://github.com/trending?since=daily";

    private HashSet<String> visitedUrls;

    Spider() {
        this.visitedUrls = new HashSet<>();
    }

    public void query() {
        query(ROOT_URL);
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
        Spider spider = new Spider();
        spider.query();
    }
}
