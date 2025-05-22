package org.informatics.utils.fileio;

import java.io.*;

import static org.informatics.Constants.*;

public abstract class FileIOUtil<T> {
    protected String read(String directoryName, String fileName, long shopId) throws IOException {
        fileName = directoryName + PATH_DELIMITER + fileName + INVOICE_FILE_NAME_DELIMITER + shopId;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            return content.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(fileNotFoundException.toString());
        } catch (IOException ioException) {
            throw new IOException(ioException.toString());
        }
    }

    protected void write(String directoryName, String fileName, T object) throws IOException {
        File directory = new File(directoryName);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(object.toString());
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(fileNotFoundException.toString());
        } catch (IOException ioException) {
            throw new IOException(ioException.toString());
        }
    }
}
