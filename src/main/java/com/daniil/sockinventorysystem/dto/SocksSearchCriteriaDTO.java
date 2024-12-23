package com.daniil.sockinventorysystem.dto;

public record SocksSearchCriteriaDTO(String color, String comparisonOperator, Integer cottonPercentage) {

    @Override
    public String toString() {
        return String.format(
                "SocksSearchCriteriaDTO[color = %s, comparisonOperator = %s, cottonPercentage = %s]",
                color, comparisonOperator, cottonPercentage);
    }
}
