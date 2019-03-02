package helpers;

import Models.Repository;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RepositoryParser implements Parser<Repository> {

    private Repository repository;
    private Element repoElement;

    private static final int REPO_PREFIX_IDX = 3;

    RepositoryParser(Element repoElement) {
        this.repoElement = repoElement;
        this.repository = new Repository();
    }

    @Override
    public Parser<Repository> parse() {
        return this.setName()
                .setDescription()
                .setLabelValuePairs()
                .setDetails();
    }

    @Override
    public Repository getType() {
        return this.repository;
    }

    private RepositoryParser setName() {
        repository.setName(repoElement.id().substring(REPO_PREFIX_IDX));
        return this;
    }

    private RepositoryParser setDescription() {
        repository.setDescription(repoElement.select("div.py-1").text());
        return this;
    }

    /**
     * Retrieves details about the number of stars and forks for a given repository in {@code repoList}.
     */
    private RepositoryParser setLabelValuePairs() {
        Elements anchors = repoElement.select("div").select("a");
        anchors.forEach(a -> {
            String label = a.select("svg").attr("aria-label");
            String labelValue = a.text();
            if (!label.isEmpty() && !labelValue.isEmpty()) {
                repository.addLabelValuePair(label, labelValue);
            }
        });
        return this;
    }

    private RepositoryParser setDetails() {
        repoElement.select("span")
                .forEach(x -> {
                    if (x.hasAttr("itemprop")) {
                        String property = x.attr("itemprop");
                        String propertyValue = x.text();
                        if (!property.isEmpty() && !propertyValue.isEmpty()) {
                            repository.addLabelValuePair(property, propertyValue);
                        }
                    } else {
                        x.getElementsContainingText("stars")
                                .forEach(e -> repository.addDetails(e.text()));
                    }
                });
        return this;
    }

    @Override
    public String toString() {
        System.out.println("Parsed repository: ");
        System.out.println(repository.toString());
        return null;
    }
}
