package com.daniil.sockinventorysystem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocksDTOTest {

    @Test
    @DisplayName("toString should return class name along with its fields and their their values")
    public void toStringTestCase() {
        SocksDTO socksDTOTest = new SocksDTO("red", 25, 125);
        String stringRepresentation = socksDTOTest.toString();

        assertEquals("SocksDTO[color = red, cottonPercentage = 25, quantity = 125]",
                stringRepresentation);
    }
}
