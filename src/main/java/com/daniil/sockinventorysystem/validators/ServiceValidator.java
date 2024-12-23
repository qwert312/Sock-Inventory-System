package com.daniil.sockinventorysystem.validators;

import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class ServiceValidator {

    public void validateSocks(Socks socks) {
        if (socks == null)
            throw new ValidationException("The passed socks object cannot be null.");

        if (socks.getColor() == null)
            throw new ValidationException("Color of the passed socks object cannot be null.");

        if (socks.getCottonPercentage() < 0 || socks.getCottonPercentage() > 100)
            throw new ValidationException(
                    "Cotton percentage of the passed socks object should be between 0 and 100 (inclusive).");

        if (socks.getQuantity() < 0)
            throw new ValidationException("Quantity of the passed socks object cannot be negative.");

    }

    public void validateSocksSearchCriteria(SocksSearchCriteria socksSearchCriteria) {
        if (socksSearchCriteria == null)
            throw new ValidationException("Passed socks search criteria cannot be null.");

        if (socksSearchCriteria.cottonPercentage() != null) {
            if (socksSearchCriteria.cottonPercentage() < 0 || socksSearchCriteria.cottonPercentage() > 100)
                throw new ValidationException(
                        "Cotton percentage of the passed socks search criteria should be between 0 and 100 " +
                                "(inclusive).");
        }
    }
}
