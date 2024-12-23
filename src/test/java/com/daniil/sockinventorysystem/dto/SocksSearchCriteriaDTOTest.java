package com.daniil.sockinventorysystem.dto;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocksSearchCriteriaDTOTest {

    @Test
    @DisplayName("toString should return class name along with its fields and their their values")
    public void toStringTestCase() {
        SocksSearchCriteriaDTO socksSearchCriteriaDTOTest =
                new SocksSearchCriteriaDTO("red", "moreThan", 125);
        String stringRepresentation = socksSearchCriteriaDTOTest.toString();

        assertEquals("SocksSearchCriteriaDTO[color = red, comparisonOperator = moreThan, cottonPercentage = 125]",
                stringRepresentation);
    }
}
