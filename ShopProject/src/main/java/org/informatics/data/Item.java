package org.informatics.data;

import java.io.Serializable;
import java.util.Date;

public record Item(
        Long id,
        String name,
        Double price,
        Category category,
        Date expiryDate
) implements Serializable {}
