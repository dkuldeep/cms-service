package com.cms.constant;

import java.util.Arrays;

public enum ToolType {
    FORMATTER("Formatter/Validators"),
    ENCODER("Encoder/Decoder"),
    STRING("String Tools"),
    UNCATEGORIZED("Miscellaneous");

    private final String label;

    ToolType(String s) {
        this.label = s;
    }

    public String getLabel() {
        return label;
    }

    public static ToolType getTypeByName(String name) {
        return Arrays.stream(values()).filter(toolType -> toolType.name().equals(name)).findFirst().orElse(UNCATEGORIZED);
    }
}
