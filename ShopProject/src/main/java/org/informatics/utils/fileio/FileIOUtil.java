package org.informatics.utils.fileio;

import java.io.*;

public abstract class FileIOUtil<T> {
    protected String read(String fileName) throws FileNotFoundException, IOException {
        String directory = "invoices";
        fileName = directory + "/" + fileName;

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

    protected T write(String fileName, T object) throws FileNotFoundException, IOException {
        String folderPath = "invoices";

        File directory = new File(folderPath);
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

        return object;
    }
}
