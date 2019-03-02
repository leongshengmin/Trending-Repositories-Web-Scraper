package helpers.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import Models.RepoList;
import Models.XmlAdaptedRepoList;

public class FileUtil {

    private static Path dir;

    public static void saveToFile(RepoList parsedRepositories, int idx) throws IOException, JAXBException {
        XmlAdaptedRepoList xmlAdaptedRepoList = new XmlAdaptedRepoList(parsedRepositories);
        XmlUtil.saveDataToFile(getPath(idx), xmlAdaptedRepoList);
    }

    public static RepoList readFromFile(int idx) throws JAXBException, IOException {
        XmlAdaptedRepoList xmlAdaptedRepoList = XmlUtil.getDataFromFile(getPath(idx), XmlAdaptedRepoList.class);
        return xmlAdaptedRepoList.toRepoList();
    }

    public static void setDir() throws IOException {
        dir = Files.createDirectory(Paths.get(".","scrapedDocuments"));
    }

    private static Path getPath(int idx) throws IOException {
        Path fileToCreatePath = dir.resolve(String.format("file%d.txt", idx));
        return fileToCreatePath;
    }

}
