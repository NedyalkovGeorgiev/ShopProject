package org.informatics.service.impl;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ShopService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ShopServiceImpl implements ShopService {
    private final Shop shop;

    public ShopServiceImpl(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void removeExpiredItem(List<Item> item) {
        for (Item itemToRemove : item) {
            shop.getAvailableItems().remove(itemToRemove);
            System.out.println("This got REMOVED: " + itemToRemove.getName());
        }
    }

    @Override
    public void countInvoices() {
        String directory = "invoices";

        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            long fileCount = paths.filter(Files::isRegularFile).count();
            System.out.println("Number of files: " + fileCount);
        } catch (IOException ioException) {
            System.out.println("No files in the desired directory: " + directory);
        }

    }
}
