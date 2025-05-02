package org.informatics.service.impl;

import org.informatics.data.ShopEntity;
import org.informatics.service.InvoiceSequenceService;

import java.util.HashMap;
import java.util.Map;

public class InvoiceSequenceImpl implements InvoiceSequenceService {
    private final Map<ShopEntity, Long> shopEntityInvoiceSequenceEntityMap = new HashMap<>();

    @Override
    public Long incrementInvoiceSequenceId(ShopEntity shopEntity) {
        return shopEntityInvoiceSequenceEntityMap.compute(
                shopEntity,
                (key, value) -> (value == null) ? 1L : value + 1
        );
    }
}
