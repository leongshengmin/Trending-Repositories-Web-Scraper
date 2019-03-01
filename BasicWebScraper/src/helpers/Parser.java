package helpers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class Parser {
    private static Logger logger = Logger.getLogger(Parser.class.getSimpleName());
    private static HashMap<String, List<String>> data = new HashMap<>();

    public static HashMap<String, List<String>> parse(Document document) {
        getDatebinsToVisit(document);

        Element repoList = document.getElementsByClass("repo-list").first();
        if (repoList == null) return data;

        for (Element repo : repoList.children()) {
            String name = repo.id().substring(2);
            System.out.println("name: " + name);
            if (repo.select("div").hasClass("g-emoji")) {
                repo.select("div.py-1").text();
            }

            Elements anchors = repo.select("div").select("a");

            System.out.println("SVG LABELS: ");
            anchors.forEach(x -> {
                System.out.print(x.select("svg").attr("aria-label"));
                System.out.println(x.text());
                System.out.println("====================");
            });

            System.out.println("SPAN TEXT");
            repo.select("span").forEach(x -> {
                if (x.select("span").hasAttr("itemprop")) {
                    System.out.println(x.text());
                }
                if (!x.select("svg").isEmpty()) {
                    System.out.println(x.text());
                }
            });

            // Elements linksOnPage = repo.select("a[href]");
            // processLinks(linksOnPage);

            //Elements svgLabels = repo.child(3).select("a").select("svg");
            //processSvgs(svgLabels);
            String text = repo.select("div").text();
            //System.out.println(text);
        }

        return data;
    }

    public static void getDatebinsToVisit(Document document) {
        document.getElementsByClass("tabnav").select("div").select("details").forEach(x -> {
            x.select("a").forEach(y -> {
                System.out.println(y.absUrl("href"));
            });
        });
    }

    private static List<String> processSvgs(Elements svgElements) {
        List<String> labels = new LinkedList<>();
        for (Element svg : svgElements) {
            if (svg.hasAttr("aria-label")) {
                String svgLabel = svg.attr("[aria-label]");
                labels.add(svgLabel);
                logger.info("svglabel: " + svgLabel);
            } else {
                logger.info("tag name: " + svg.tagName() + " node name: " + svg.nodeName() + " own text " + svg.text());
            }
        }
        return labels;
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
