package com.daniil.sockinventorysystem.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocksTest {

    @Test
    @DisplayName("toString should return class name along with its fields and their their values")
    public void toStringTestCase() {
        Socks socksTest = new Socks("red", 25, 125);
        socksTest.setId(5);
        String stringRepresentation = socksTest.toString();

        assertEquals("Socks[id = 5, color = red, cottonPercentage = 25, quantity = 125]",
                stringRepresentation);
    }
}
