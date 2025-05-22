package org.informatics.data;

import java.util.Date;

public record Item(
        Long id,
        String name,
        Double price,
        Category category,
        Date expiryDate
) {}
