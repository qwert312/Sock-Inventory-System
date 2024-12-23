package com.daniil.sockinventorysystem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ComparisonOperatorTest {

    @Test
    @DisplayName("getByName should return MORE_THAN enum if input is moreThan string")
    public void getByNamePositiveTestCase1() {
        assertEquals(ComparisonOperator.MORE_THAN, ComparisonOperator.getByName("moreThan"));
    }

    @Test
    @DisplayName("getByName should return LESS_THAN enum if input is lessThan string")
    public void getByNamePositiveTestCase2() {
        assertEquals(ComparisonOperator.LESS_THAN, ComparisonOperator.getByName("lessThan"));
    }

    @Test
    @DisplayName("getByName should return EQUALS enum if input is equals string")
    public void getByNamePositiveTestCase3() {
        assertEquals(ComparisonOperator.EQUALS, ComparisonOperator.getByName("equals"));
    }

    @Test
    @DisplayName("getByName should return null if input is none of expected strings")
    public void getByNameNegativeTestCase1() {
        assertNull(ComparisonOperator.getByName("12345"));
    }

    @Test
    @DisplayName("getByName should return null if input is null")
    public void getByNameNegativeTestCase2() {
        assertNull(ComparisonOperator.getByName(null));
    }

    @Test
    @DisplayName("toString should return string representation moreThan for enum value MORE_THAN")
    public void toStringTestCase1() {
        assertEquals("moreThan", ComparisonOperator.MORE_THAN.toString());
    }

    @Test
    @DisplayName("toString should return string representation lessThan for enum value LESS_THAN")
    public void toStringTestCase2() {
        assertEquals("lessThan", ComparisonOperator.LESS_THAN.toString());
    }

    @Test
    @DisplayName("toString should return string representation equals for enum value EQUALS")
    public void toStringTestCase3() {
        assertEquals("equals", ComparisonOperator.EQUALS.toString());
    }
}
