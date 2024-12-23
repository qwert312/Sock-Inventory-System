package com.daniil.sockinventorysystem.domain;

import com.daniil.sockinventorysystem.domain.entities.Socks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocksSearchCriteriaTest {

    @Test
    @DisplayName("toString should return class name along with its fields and their their values")
    public void toStringTestCase() {
        SocksSearchCriteria socksSearchCriteriaTest =
                new SocksSearchCriteria("red", ComparisonOperator.MORE_THAN, 125);
        String stringRepresentation = socksSearchCriteriaTest.toString();

        assertEquals("SocksSearchCriteria[color = red, comparisonOperator = moreThan, cottonPercentage = 125]",
                stringRepresentation);
    }
}
