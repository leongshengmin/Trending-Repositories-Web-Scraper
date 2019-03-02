package Models;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlValue;

public class XmlAdaptedRepoList {

    @XmlValue
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
