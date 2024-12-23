package com.daniil.sockinventorysystem.mappers;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.dto.SocksDTO;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.dto.SocksSearchCriteriaDTO;
import com.daniil.sockinventorysystem.exceptions.MappingException;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import com.daniil.sockinventorysystem.validators.DTOMapperValidator;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {

    public DTOMapperValidator dtoMapperValidator;

    public DTOMapper(DTOMapperValidator dtoMapperValidator) {
        this.dtoMapperValidator = dtoMapperValidator;
    }

    public Socks dtoToSocks(SocksDTO socksDTO) {
        try {
            dtoMapperValidator.validateSocksDTO(socksDTO);
        } catch (ValidationException ex) {
            throw new MappingException(ex.getMessage(), ex);
        }

        return new Socks(socksDTO.color(), socksDTO.cottonPercentage(), socksDTO.quantity());
    }

    public SocksDTO socksToDto(Socks socks) {
        try {
            dtoMapperValidator.validateSocks(socks);
        } catch (ValidationException ex) {
            throw new MappingException(ex.getMessage(), ex);
        }

        return new SocksDTO(socks.getColor(), socks.getCottonPercentage(),
                socks.getQuantity());
    }

    public SocksSearchCriteria dtoToSocksSearchCriteria(SocksSearchCriteriaDTO socksSearchCriteriaDTO) {
        try {
            dtoMapperValidator.validateSocksSearchCriteriaDTO(socksSearchCriteriaDTO);
        } catch (ValidationException ex) {
            throw new MappingException(ex.getMessage(), ex);
        }

        String comparisonOperatorDTO = socksSearchCriteriaDTO.comparisonOperator();
        ComparisonOperator comparisonOperator = null;

        if (comparisonOperatorDTO != null) {
            comparisonOperator = ComparisonOperator.getByName(comparisonOperatorDTO);
            if (comparisonOperator == null)
                throw new MappingException("Incorrect comparison operator value.");
        }

        return new SocksSearchCriteria(
                socksSearchCriteriaDTO.color(),
                comparisonOperator,
                socksSearchCriteriaDTO.cottonPercentage());
    }

    public SocksSearchCriteriaDTO socksToSearchCriteriaDto(SocksSearchCriteria socksSearchCriteria) {
        try {
            dtoMapperValidator.validateSocksSearchCriteria(socksSearchCriteria);
        } catch (ValidationException ex) {
            throw new MappingException(ex.getMessage(), ex);
        }

        ComparisonOperator comparisonOperator = socksSearchCriteria.comparisonOperator();
        String comparisonOperatorDTO = null;

        if (comparisonOperator != null)
            comparisonOperatorDTO = comparisonOperator.toString();


        return new SocksSearchCriteriaDTO(
                socksSearchCriteria.color(),
                comparisonOperatorDTO,
                socksSearchCriteria.cottonPercentage());
    }
}
