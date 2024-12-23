package com.daniil.sockinventorysystem.domain;

public enum ComparisonOperator {
    MORE_THAN("moreThan"),
    LESS_THAN("lessThan"),
    EQUALS("equals");

    private String name;

    ComparisonOperator(String name) {
        this.name = name;
    }

    public static ComparisonOperator getByName(String name) {
        if (name == null)
            return null;

        return switch (name) {
            case "moreThan" -> ComparisonOperator.MORE_THAN;
            case "lessThan" -> ComparisonOperator.LESS_THAN;
            case "equals" -> ComparisonOperator.EQUALS;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return this.name;
    }
}
