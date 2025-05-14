package org.informatics.service.impl;

import org.informatics.data.Shop;
import org.informatics.service.InvoiceSequenceService;

import java.util.HashMap;
import java.util.Map;

public class InvoiceSequenceServiceImpl implements InvoiceSequenceService {
    private final Map<Shop, Long> shopEntityInvoiceSequenceEntityMap = new HashMap<>();

    @Override
    public Long incrementInvoiceSequenceId(Shop shop) {
        return shopEntityInvoiceSequenceEntityMap.compute(
                shop,
                (key, value) -> (value == null) ? 1L : value + 1
        );
    }
}
