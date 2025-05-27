package org.informatics.service.impl;

import org.informatics.data.Invoice;
import org.informatics.service.InvoiceSerializationDeserializationService;

import java.io.*;

public class InvoiceSerializationDeserializationServiceImpl implements InvoiceSerializationDeserializationService {

    @Override
    public void serializeInvoice(String filename, Invoice invoice) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(invoice);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize invoice to file: " + filename, e);
        }
    }

    @Override
    public Invoice deserializeInvoice(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (Invoice) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize invoice from file: " + fileName, e);
        }
    }
}