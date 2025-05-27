package org.informatics.service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.exceptions.NoDeliveredItemsException;
import org.informatics.exceptions.NoEmployeesException;
import org.informatics.service.ItemService;
import org.informatics.service.ShopService;
import java.io.*;
import java.util.List;
import java.util.Map;

import static org.informatics.Constants.*;
import static org.informatics.Constants.PERCENT;

public class ShopServiceImpl implements ShopService {
    private final ItemService itemService;

    public ShopServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void removeExpiredItem(Shop shop, List<Item> itemsToRemove) {
        for (Item itemToRemove : itemsToRemove) {
            List<Item> itemList = shop.getAvailableItems().get(itemToRemove.name());
            if (itemList != null) {
                itemList.removeIf(i -> i.equals(itemToRemove));
                if (itemList.isEmpty()) {
                    shop.getAvailableItems().remove(itemToRemove.name());
                }
                System.out.println("This got REMOVED: " + itemToRemove.name());
            }
        }
    }

    @Override
    public void countInvoices(long shopId) {
        File directory = new File(INVOICE_DIRECTORY_NAME);
        FilenameFilter filenameFilter = (dir, name) -> name.endsWith("_" + shopId);
        File[] invoiceFiles = directory.listFiles(filenameFilter);

        if (invoiceFiles == null || invoiceFiles.length == 0) {
            System.out.println("No invoice found for shopId: " + shopId);
            return;
        }

        System.out.println("Shop with Id:" + shopId + " has " + invoiceFiles.length + " invoices.");
    }

    @Override
    public void calculateTotalIncome(long shopId) {
        File directory = new File(INVOICE_DIRECTORY_NAME);
        FilenameFilter filenameFilter = (dir, name) -> name.endsWith("_" + shopId);
        File[] invoiceFiles = directory.listFiles(filenameFilter);
        double invoiceTotalPrice = 0D;

        if (invoiceFiles == null || invoiceFiles.length == 0) {
            System.out.println("No invoice found for shopId: " + shopId);
            return;
        }

        for (File invoice : invoiceFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(invoice))) {
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(TOTAL)) {
                        content.append(line);
                    }
                }

                invoiceTotalPrice += Double.parseDouble(content.delete(0, TOTAL.length()).toString().trim());
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File with name: " + invoice.getName() + " not found!");
                return;
            } catch (IOException ioException) {
                System.out.println("Could not read file with name: " + invoice.getName() + "!");
                return;
            }
        }

        System.out.println("Shop with Id:" + shopId + " has a total income of: " + invoiceTotalPrice);
    }

    @Override
    public void calculateExpenses(Shop shop) {
        double totalExpenses = 0;

        try {
            totalExpenses += calculateSalaryExpenses(shop);
            totalExpenses += calculateDeliveryExpenses(shop);
        } catch (NoEmployeesException noEmployeesException) {
            System.out.println("Shop with Id:" + shop.getId() + " has no employees!");
            return;
        } catch (NoDeliveredItemsException noDeliveredItemsException) {
            System.out.println("Shop with Id:" + shop.getId() + " has no delivered items!");
            return;
        }

        System.out.println("Shop with Id:" + shop.getId() + " has " + totalExpenses + " expenses.");
    }

    private double calculateSalaryExpenses(Shop shop) throws NoEmployeesException {
        List<Employee> employees = shop.getEmployees();

        if (employees == null || employees.isEmpty()) {
            throw new NoEmployeesException("Shop with Id:" + shop.getId() + " has no employees!");
        }

        return employees.stream()
                .map(Employee::salary)
                .reduce(0D, Double::sum);
    }

    private double calculateDeliveryExpenses(Shop shop) throws NoDeliveredItemsException {
        Map<String, List<Item>> deliveredItems = shop.getDeliveredItems();

        if (deliveredItems.isEmpty()) {
            throw new NoDeliveredItemsException("Shop with Id: " + shop.getId() + " has no delivered items!");
        }

        return deliveredItems.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Item::price)
                .sum();
    }

    @Override
    public Double calculateMarkup(Double price, Double markup) {
        return price + price * markup/PERCENT;
    }

    @Override
    public Double calculateDiscount(Double price, Item item) {
        double discount = itemService.getDiscount(item);
        return price * discount/PERCENT;
    }
}
