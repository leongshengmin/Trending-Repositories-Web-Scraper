import java.io.IOException;
import java.util.HashSet;

import javax.xml.bind.JAXBException;

import helpers.Crawler;
import helpers.DocumentParser;
import helpers.utils.FileUtil;
import org.jsoup.nodes.Document;

public class Spider {

    private static final String ROOT_URL ="https://github.com/trending?since=daily";
    private HashSet<String> visitedUrls;
    private int counter = 0;

    Spider() {
        this.visitedUrls = new HashSet<>();
    }

    public void query() throws IOException, JAXBException {
        query(ROOT_URL);
    }

    private void query(String link) throws IOException, JAXBException {
        visitedUrls.add(link);
        Document document = Crawler.retrieveDocument(link);
        DocumentParser documentParser = ((DocumentParser) (new DocumentParser(document).parse()));
        System.out.println(documentParser);  // print parsed contents
        FileUtil.saveToFile(documentParser.getParsedRepositories(), counter++);

        for (String toVisit : documentParser.getLinksToVisit()) {
            if (!visitedUrls.contains(toVisit)) {
                query(toVisit);
            }
        }
    }

    public static void main(String[] args) {
        Spider spider = new Spider();
        try {
            FileUtil.setDir();
            spider.query();
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
