package com.daniil.sockinventorysystem.dto;


public record SocksDTO(String color, Integer cottonPercentage, Integer quantity) {

    @Override
    public String toString() {
        return String.format(
                "SocksDTO[color = %s, cottonPercentage = %s, quantity = %s]",
                color, cottonPercentage, quantity);
    }
}
