package helpers.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import Models.RepoList;
import Models.XmlAdaptedRepoList;

public class FileUtil {

    private static final Path DEFAULT_PATH = Paths.get("..", "scrapedDocuments");

    public static void saveToFile(RepoList parsedRepositories) throws IOException, JAXBException {
        XmlAdaptedRepoList xmlAdaptedRepoList = new XmlAdaptedRepoList(parsedRepositories);
        XmlUtil.saveDataToFile(DEFAULT_PATH, xmlAdaptedRepoList);
    }

    public static RepoList readFromFile() throws JAXBException, FileNotFoundException {
        XmlAdaptedRepoList xmlAdaptedRepoList = XmlUtil.getDataFromFile(DEFAULT_PATH, XmlAdaptedRepoList.class);
        return xmlAdaptedRepoList.toRepoList();
    }
}
