package Models;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;

public class XmlAdaptedRepo {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private HashMap<String, String> labelValuePairs;
    @XmlElement(required = true)
    private LinkedList<String> details;

    public XmlAdaptedRepo() {}

    public XmlAdaptedRepo(Repository repository) {
        this.name = repository.getName();
        this.description = repository.getDescription();
        this.labelValuePairs = repository.getLabelValuePairs();
        this.details = repository.getDetails();
        requireNonNull(name);
        requireNonNull(description);
        requireNonNull(labelValuePairs);
        requireNonNull(details);
    }

    public Repository toRepository() {
        return new Repository(name, description, labelValuePairs, details);
    }

}
