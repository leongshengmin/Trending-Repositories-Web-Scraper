package helpers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class Parser {

    static HashMap<String, List<String>> data = new HashMap<>();

    public static HashMap<String, List<String>> parse(Document document) {
        Element repoList = document.getElementsByClass("repo-list").first();

        for (Element repo : repoList.children()) {
            String name = repo.id().substring(2);
            // Elements linksOnPage = repo.select("a[href]");
            // processLinks(linksOnPage);

            List<TextNode> textNodes = repo.textNodes();
            List<String> processedText = processText(textNodes);

            data.put(name, processedText);
        }

        return data;
    }

    private static List<String> processLinks(Elements linksOnPage) {
        List<String> links = new LinkedList<>();
        for (Element link : linksOnPage) {
            // if not yet visited
            links.add(link.absUrl("href"));
        }
        return links;
    }

    private static List<String> processText(List<TextNode> textNodes) {
        List<String> textualInfo = new LinkedList<>();
        for (TextNode textNode : textNodes) {
            // filter for text
            textualInfo.add(textNode.text());
        }
        return textualInfo;
    }
}
