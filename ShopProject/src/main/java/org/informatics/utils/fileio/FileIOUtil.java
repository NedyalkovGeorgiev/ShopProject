package org.informatics.utils.fileio;

import java.io.*;

public abstract class FileIOUtil<T> {
    protected String read(String fileName) {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            return content.toString();
        } catch (FileNotFoundException e) {
            //TODO: Exception Handling
            throw new RuntimeException(e);
        } catch (IOException e) {
            //TODO: Exception Handling
            throw new RuntimeException(e);
        }
    }

    protected T write(String fileName, T object) {
        File file = new File(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(object.toString());
        } catch (FileNotFoundException e) {
            //TODO: Exception Handling
            throw new RuntimeException(e);
        } catch (IOException e) {
            //TODO: Exception Handling
            throw new RuntimeException(e);
        }

        return object;
    }
}
