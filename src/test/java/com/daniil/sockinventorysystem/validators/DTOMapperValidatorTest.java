package com.daniil.sockinventorysystem.validators;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.dto.SocksDTO;
import com.daniil.sockinventorysystem.dto.SocksSearchCriteriaDTO;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DTOMapperValidatorTest {

    DTOMapperValidator dtoMapperValidator = new DTOMapperValidator();

    @Test
    @DisplayName("validateSocksDTO should do nothing if SocksDTO and it's fields aren't null")
    public void validateSocksDTOPositiveTestCase() {
        dtoMapperValidator.validateSocksDTO(new SocksDTO("red", 10, 25));
    }

    @Test
    @DisplayName("validateSocksDTO should throw ValidationException if SocksDTO is null")
    public void validateSocksDTONegativeTestCase1() {
        assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksDTO(null));
    }
    @Test
    @DisplayName("validateSocksDTO should throw ValidationException if any of SocksDTO fields are null")
    public void validateSocksDTONegativeTestCase2() {
        SocksDTO socksDTOTest1 = new SocksDTO(null, 10, 20);
        SocksDTO socksDTOTest2 = new SocksDTO("blue", null, 20);
        SocksDTO socksDTOTest3 = new SocksDTO("red", 10, null);

        assertAll(
                () ->
                assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksDTO(socksDTOTest1)),
                () ->
                assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksDTO(socksDTOTest2)),
                () ->
                assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksDTO(socksDTOTest3)));
    }

    @Test
    @DisplayName("validateSocks should do nothing if Socks and it's color aren't null")
    public void validateSocksPositiveTestCase() {
        dtoMapperValidator.validateSocks(new Socks("red", 10, 25));
    }

    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks is null")
    public void validateSocksNegativeTestCase1() {
        assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocks(null));
    }
    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks color is null")
    public void validateSocksNegativeTestCase2() {
        assertThrows(ValidationException.class,
                () -> dtoMapperValidator.validateSocks(new Socks(null, 10, 20)));
    }

    @Test
    @DisplayName("validateSocksSearchCriteriaDTO should do nothing if SocksSearchCriteriaDTO isn't null")
    public void validateSocksSearchCriteriaDTOPositiveTestCase() {
        dtoMapperValidator.validateSocksSearchCriteriaDTO(
                new SocksSearchCriteriaDTO("red", "moreThan", 50));
    }

    @Test
    @DisplayName("validateSocksSearchCriteriaDTO should throw ValidationException if " +
            "SocksSearchCriteriaDTO is null")
    public void validateSocksSearchCriteriaDTONegativeTestCase() {
        assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksSearchCriteriaDTO(null));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should do nothing if SocksSearchCriteria isn't null")
    public void validateSocksSearchCriteriaPositiveTestCase() {
        dtoMapperValidator.validateSocksSearchCriteria(
                new SocksSearchCriteria("red", ComparisonOperator.MORE_THAN, 50));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should throw ValidationException if " +
            "SocksSearchCriteria is null")
    public void validateSocksSearchCriteriaNegativeTestCase() {
        assertThrows(ValidationException.class, () -> dtoMapperValidator.validateSocksSearchCriteria(null));
    }
}
