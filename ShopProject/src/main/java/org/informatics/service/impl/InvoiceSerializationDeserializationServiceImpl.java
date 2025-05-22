package org.informatics.service.impl;

import org.informatics.data.Invoice;
import org.informatics.service.InvoiceSerializationDeserializationService;
import java.io.*;

public class InvoiceSerializationDeserializationServiceImpl implements InvoiceSerializationDeserializationService {
    @Override
    public void serializeInvoice(String filename, Invoice invoice) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(invoice);
        } catch (IOException ioException) {
            System.out.println("General IO exception!");
        }
    }

    @Override
    public Invoice deserializeInvoice(String fileName) {
        Invoice invoice = null;

        try(FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            invoice = (Invoice) objectInputStream.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Could not find class with name: " + fileName);
        } catch (IOException ioException) {
            System.out.println("General IO exception!");
        }

        return invoice;
    }
}
