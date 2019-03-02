package Models;

import java.io.Serializable;
import java.util.LinkedList;

public class Repository implements Serializable {
    private String name;
    private String description;
    private LinkedList<String> details;

    public Repository() {
        this.details = new LinkedList<>();
    }

    public Repository(String name, String description, LinkedList<String> details) {
        this.name = name;
        this.description = description;
        this.details = details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addDetails(String details) {
        this.details.add(details);
    }

    public void addLabelValuePair(String label, String value) {
        addDetails(String.format("%s: %s", label, value));
    }

    @Override
    public String toString() {
        String repoNameDesc = String.format("Name: %s, \nDescription: %s\n", name, description);
        System.out.println(repoNameDesc);
        System.out.println("Details: ");
        details.forEach(System.out::println);
        return repoNameDesc;
    }

    public LinkedList<String> getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
