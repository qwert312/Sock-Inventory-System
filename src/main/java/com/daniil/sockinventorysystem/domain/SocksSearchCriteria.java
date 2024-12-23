package com.daniil.sockinventorysystem.domain;

public record SocksSearchCriteria(String color, ComparisonOperator comparisonOperator, Integer cottonPercentage) {
    @Override
    public String toString() {
        return String.format(
                "SocksSearchCriteria[color = %s, comparisonOperator = %s, cottonPercentage = %s]",
                color, comparisonOperator, cottonPercentage);
    }
}
