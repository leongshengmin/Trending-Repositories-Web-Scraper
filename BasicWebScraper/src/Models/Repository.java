package Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Repository implements Serializable {
    private String name;
    private String description;
    private HashMap<String, String> labelValuePairs;
    private LinkedList<String> details;

    public Repository() {
        this.labelValuePairs = new HashMap<>();
        this.details = new LinkedList<>();
    }

    public Repository(String name, String description, HashMap<String, String> labelValuePairs, LinkedList<String> details) {
        this.name = name;
        this.description = description;
        this.details = details;
        this.labelValuePairs = labelValuePairs;
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

    public boolean addLabelValuePair(String label, String value) {
        if (labelValuePairs.containsKey(label)) {
            return false;
        }
        labelValuePairs.put(label, value);
        return true;
    }

    @Override
    public String toString() {
        String repoNameDesc = String.format("Name: %s, \nDescription: %s\n", name, description);
        System.out.println(repoNameDesc);
        labelValuePairs.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " : " + e.getValue());
        });
        System.out.println("Details: ");
        details.forEach(System.out::println);
        return repoNameDesc;
    }

    public HashMap<String, String> getLabelValuePairs() {
        return labelValuePairs;
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
