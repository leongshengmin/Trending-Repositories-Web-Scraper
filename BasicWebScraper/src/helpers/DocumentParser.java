package helpers;

import java.util.LinkedList;
import java.util.logging.Logger;

import Models.Repository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocumentParser implements Parser<Document> {

    private static Logger logger = Logger.getLogger(DocumentParser.class.getSimpleName());

    private Document document;
    private LinkedList<Repository> parsedRepositories;
    private LinkedList<String> linksToVisit;

    public DocumentParser(Document document) {
        this.document = document;
        this.parsedRepositories = new LinkedList<>();
        this.linksToVisit = new LinkedList<>();
    }

    @Override
    public Parser<Document> parse() {
        logger.info("Parsing links in document.");
        this.setLinksToVisit();

        Element repoList = getRepoList(document);
        if (repoList != null) {
            logger.info("Parsing list of repositories.");
            for (Element repoElement : repoList.children()) {
                RepositoryParser repositoryParser = new RepositoryParser(repoElement);
                Repository parsed = repositoryParser.parse().getType();
                parsedRepositories.add(parsed);
                System.out.println(parsed);
            }
        }
        return this;
    }

    @Override
    public Document getType() {
        return document;
    }

    public LinkedList<Repository> getParsedRepositories() {
        return this.parsedRepositories;
    }

    public LinkedList<String> getLinksToVisit() {
        return linksToVisit;
    }

    /**
     * Retrieves urls of trending repos grouped by datebin.
     */
    private void setLinksToVisit() {
        document.getElementsByClass("tabnav").select("details").forEach(x -> {
            x.select("a").forEach(a -> {
                this.linksToVisit.add(a.absUrl("href"));
            });
        });
    }

    /**
     * Retrieves a list of repositories for the queried datebin in the given document.
     *
     * @param document queried document.
     * @return list of repositories.
     */
    private static Element getRepoList(Document document) {
        Elements repoListElements = document.getElementsByClass("repo-list");
        if (repoListElements == null || repoListElements.first() == null) {
            logger.info("No retrievable list of repositories");
            return null;
        }
        logger.info("Retrieved list of repositories");
        return repoListElements.first();
    }

    @Override
    public String toString() {
        System.out.println("Parsed repositories: ");
        parsedRepositories.forEach(repo -> repo.toString());
        System.out.println("Links to visit: ");
        linksToVisit.forEach(link -> System.out.println(link));
        return null;
    }
}
