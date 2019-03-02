package Models;

import java.util.HashMap;
import java.util.LinkedList;

public class Repository {
    private String name;
    private String description;
    private HashMap<String, String> labelValuePairs;
    private LinkedList<String> details;

    public Repository() {
        this.labelValuePairs = new HashMap<>();
        this.details = new LinkedList<>();
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
}
