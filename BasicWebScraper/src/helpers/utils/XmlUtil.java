package helpers.utils;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Helps with reading from and writing to XML files.
 */
public class XmlUtil {

    /**
     * Returns the xml data in the file as an object of the specified type.
     *
     * @param file           Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct FORMAT.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(Path file, Class<T> classToConvert)
            throws JAXBException, FileNotFoundException {
        requireNonNull(file);
        requireNonNull(classToConvert);
        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(file.toFile()));
    }

    /**
     * Saves the data in the file in xml FORMAT.
     *
     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if there is an error during converting the data
     *                               into xml and writing to the file.
     */
    public static <T> void saveDataToFile(Path file, T data) throws IOException, JAXBException {
        requireNonNull(file);
        requireNonNull(data);

        if (!Files.exists(file)) {
            Files.createFile(file);
        }

        // converts content of {@code data's} class into a properly formatted FXML file
        JAXBContext context = JAXBContext.newInstance( data.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // saves FXML data into a file at the corresponding filepath
        m.marshal(data, file.toFile());
    }

}
