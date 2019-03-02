package Models;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Repositories")
public class XmlAdaptedRepoList {

    @XmlElement
    private LinkedList<XmlAdaptedRepo> xmlAdaptedRepos;

    public XmlAdaptedRepoList() {
        this.xmlAdaptedRepos = new LinkedList<>();
    }

    public XmlAdaptedRepoList(RepoList repoList) {
        this();
        repoList.getParsedRepositories().forEach(x -> this.xmlAdaptedRepos.add(new XmlAdaptedRepo(x)));
    }

    public RepoList toRepoList() {
        RepoList repoList = new RepoList();
        xmlAdaptedRepos.forEach(x -> repoList.addRepo(x.toRepository()));
        return repoList;
    }
}
