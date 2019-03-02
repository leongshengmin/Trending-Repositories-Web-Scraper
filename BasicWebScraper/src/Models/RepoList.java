package Models;

import java.io.Serializable;
import java.util.LinkedList;

public class RepoList implements Serializable {
    private LinkedList<Repository> parsedRepositories;

    public RepoList() {
        this.parsedRepositories = new LinkedList<>();
    }

    public RepoList(LinkedList<Repository> repositories) {
        this.parsedRepositories = repositories;
    }

    public void addRepo(Repository repository) {
        this.parsedRepositories.add(repository);
    }

    public LinkedList<Repository> getParsedRepositories() {
        return parsedRepositories;
    }
}
