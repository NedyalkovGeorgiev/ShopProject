package org.informatics.data;

import java.io.Serializable;

public record Employee(
        String name,
        Long id,
        Double salary
) implements Serializable {}
