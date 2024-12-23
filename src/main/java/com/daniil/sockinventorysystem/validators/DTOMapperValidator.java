package com.daniil.sockinventorysystem.validators;

import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.dto.SocksDTO;
import com.daniil.sockinventorysystem.dto.SocksSearchCriteriaDTO;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DTOMapperValidator {

    public void validateSocksDTO(SocksDTO socksDTO) {
        if (socksDTO == null)
            throw new ValidationException("Passed to map socks DTO cannot be null.");

        if (socksDTO.color() == null || socksDTO.cottonPercentage() == null || socksDTO.quantity() == null)
            throw new ValidationException("Fields of passed socks DTO cannot be null.");
    }

    public void validateSocks(Socks socks) {
        if (socks == null)
            throw new ValidationException("Passed to map socks object cannot be null.");

        if (socks.getColor() == null)
            throw new ValidationException("Passed to map socks object cannot have a null color.");
    }

    public void validateSocksSearchCriteriaDTO(SocksSearchCriteriaDTO socksSearchCriteriaDTO) {
        if (socksSearchCriteriaDTO == null)
            throw new ValidationException("Passed to map socks search criteria DTO cannot be null.");
    }

    public void validateSocksSearchCriteria(SocksSearchCriteria socksSearchCriteria) {
        if (socksSearchCriteria == null)
            throw new ValidationException("Passed to map socks search criteria cannot be null.");
    }
}
