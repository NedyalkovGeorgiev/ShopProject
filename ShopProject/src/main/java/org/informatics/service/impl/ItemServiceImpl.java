package org.informatics.service.impl;

import org.informatics.service.ItemService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ItemServiceImpl implements ItemService {

    @Override
    public Double getDiscount(Date expiryDate) {
        LocalDate expiryDateLocalDate = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (LocalDate.now().isAfter(expiryDateLocalDate.minusMonths(1L))) {
            return 20D;
        } else {
            return 0D;
        }
    }
}
