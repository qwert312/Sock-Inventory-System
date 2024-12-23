package com.daniil.sockinventorysystem.validators;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceValidatorTest {

    ServiceValidator serviceValidator = new ServiceValidator();

    @Test
    @DisplayName("validateSocks should do nothing if Socks and it's color aren't null, " +
            "quantity isn't negative and cotton percentage is in 0-100 range")
    public void validateSocksPositiveTestCase() {
        serviceValidator.validateSocks(new Socks("red", 50, 20));
    }

    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks is null")
    public void validateSocksNegativeTestCase1() {
        assertThrows(ValidationException.class, () -> serviceValidator.validateSocks(null));
    }

    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks color is null")
    public void validateSocksNegativeTestCase2() {
        assertThrows(ValidationException.class, () ->
                serviceValidator.validateSocks(new Socks(null, 20, 50)));
    }

    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks cotton percentage isn't in 0-100 range")
    public void validateSocksNegativeTestCase3() {
        Socks testSocks1 = new Socks("red", -1, 25);
        Socks testSocks2 = new Socks("red", 101, 25);

        assertAll(
                () ->
                        assertThrows(ValidationException.class, () -> serviceValidator.validateSocks(testSocks1)),
                () ->
                        assertThrows(ValidationException.class, () -> serviceValidator.validateSocks(testSocks2)));
    }

    @Test
    @DisplayName("validateSocks should throw ValidationException if Socks quantity is negative")
    public void validateSocksNegativeTestCase4() {
        assertThrows(ValidationException.class, () ->
                serviceValidator.validateSocks(new Socks("red", 25, -2)));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should do nothing if SocksSearchCriteria isn't null, " +
            "and cotton percentage is null")
    public void validateSocksSearchCriteriaPositiveTestCase1() {
        serviceValidator.validateSocksSearchCriteria(
                new SocksSearchCriteria(null, null, null));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should do nothing if SocksSearchCriteria isn't null " +
            "and cotton percentage field is in 0-100 range (if it is not null)")
    public void validateSocksSearchCriteriaPositiveTestCase2() {
        serviceValidator.validateSocksSearchCriteria(
                new SocksSearchCriteria(null, null, 20));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should throw ValidationException if SocksSearchCriteria is null")
    public void validateSocksSearchCriteriaNegativeTestCase1() {
        assertThrows(ValidationException.class, () ->
                serviceValidator.validateSocksSearchCriteria(null));
    }

    @Test
    @DisplayName("validateSocksSearchCriteria should throw ValidationException if " +
            "SocksSearchCriteria cotton percentage isn't null and it isn't in 0-100 range")
    public void validateSocksSearchCriteriaNegativeTestCase2() {
        SocksSearchCriteria testSocksSearchCriteria1 =
                new SocksSearchCriteria("red", ComparisonOperator.MORE_THAN, -1);
        SocksSearchCriteria testSocksSearchCriteria2 =
                new SocksSearchCriteria("red", ComparisonOperator.MORE_THAN, 101);

        assertAll(
                () ->
                        assertThrows(ValidationException.class, () ->
                                serviceValidator.validateSocksSearchCriteria(testSocksSearchCriteria1)),
                () ->
                        assertThrows(ValidationException.class, () ->
                                serviceValidator.validateSocksSearchCriteria(testSocksSearchCriteria2)));
    }
}
