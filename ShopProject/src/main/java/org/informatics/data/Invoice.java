package org.informatics.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public record Invoice(
        Long id,
        Employee employee,
        Date date,
        Double totalPrice,
        Map<String, List<Item>> invoiceItemData
) implements Serializable {}
