package com.daniil.sockinventorysystem.mappers;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.dto.SocksDTO;
import com.daniil.sockinventorysystem.dto.SocksSearchCriteriaDTO;
import com.daniil.sockinventorysystem.exceptions.MappingException;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import com.daniil.sockinventorysystem.validators.DTOMapperValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class DTOMapperTest {

    @Mock
    DTOMapperValidator dtoMapperValidator;

    @InjectMocks
    DTOMapper dtoMapper;

    @Test
    @DisplayName("dtoToSocks should return Socks with DTO data " +
            "if validation component doesn't throws ValidationException")
    public void dtoToSocksPositiveTestCase() {
        SocksDTO socksDTOTest = new SocksDTO("red", 25, 50);
        Socks socksResult = dtoMapper.dtoToSocks(socksDTOTest);

        assertAll(
                () -> assertEquals(socksDTOTest.color(), socksResult.getColor()),
                () -> assertEquals(socksDTOTest.cottonPercentage(), socksResult.getCottonPercentage()),
                () -> assertEquals(socksDTOTest.quantity(), socksResult.getQuantity()));
    }

    @Test
    @DisplayName("dtoToSocks should throw MappingException if validation component throws ValidationException")
    public void dtoToSocksNegativeTestCase() {
        doThrow(ValidationException.class).when(dtoMapperValidator).validateSocksDTO(nullable(SocksDTO.class));

        assertThrows(MappingException.class, () -> dtoMapper.dtoToSocks(null));
    }

    @Test
    @DisplayName("socksToDto should return SocksDTO with Socks data " +
            "if validation component doesn't throws ValidationException")
    public void socksToDtoPositiveTestCase() {
        Socks socksTest = new Socks("red", 25, 50);
        SocksDTO socksDTOResult = dtoMapper.socksToDto(socksTest);

        assertAll(
                () -> assertEquals(socksTest.getColor(), socksDTOResult.color()),
                () -> assertEquals(socksTest.getCottonPercentage(), socksDTOResult.cottonPercentage()),
                () -> assertEquals(socksTest.getQuantity(), socksDTOResult.quantity()));
    }

    @Test
    @DisplayName("socksToDto should throw MappingException if validation component throws ValidationException")
    public void socksToDtoNegativeTestCase() {
        doThrow(ValidationException.class).when(dtoMapperValidator).validateSocks(nullable(Socks.class));

        assertThrows(MappingException.class, () -> dtoMapper.socksToDto(null));
    }

    @Test
    @DisplayName("dtoToSocksSearchCriteria should return SocksSearchCriteria with SocksSearchCriteriaDTO data " +
            "if validation component doesn't throws ValidationException " +
            "and comparison operator is null")
    public void dtoToSocksSearchCriteriaPositiveTestCase1() {
        SocksSearchCriteriaDTO socksSearchCriteriaDTO =
                new SocksSearchCriteriaDTO("red", null, 50);
        SocksSearchCriteria socksSearchCriteriaResult = dtoMapper.dtoToSocksSearchCriteria(socksSearchCriteriaDTO);

        assertAll(
                () -> assertEquals(socksSearchCriteriaDTO.color(), socksSearchCriteriaResult.color()),
                () -> assertNull(socksSearchCriteriaResult.comparisonOperator()),
                () -> assertEquals(socksSearchCriteriaDTO.cottonPercentage(), socksSearchCriteriaResult.cottonPercentage()));
    }

    @Test
    @DisplayName("dtoToSocksSearchCriteria should return SocksSearchCriteria with SocksSearchCriteriaDTO data " +
            "if validation component doesn't throws ValidationException " +
            "and comparison operator mapping result isn't null (if it is not null)")
    public void dtoToSocksSearchCriteriaPositiveTestCase2() {
        SocksSearchCriteriaDTO socksSearchCriteriaDTO =
                new SocksSearchCriteriaDTO("red", "lessThan", 50);

        try (MockedStatic<ComparisonOperator> comparisonOperatorMockedStatic = mockStatic(ComparisonOperator.class)) {
            comparisonOperatorMockedStatic.when(() -> ComparisonOperator.getByName(any(String.class)))
                    .thenReturn(ComparisonOperator.LESS_THAN);
            SocksSearchCriteria socksSearchCriteriaResult = dtoMapper.dtoToSocksSearchCriteria(socksSearchCriteriaDTO);
            assertAll(
                    () -> assertEquals(socksSearchCriteriaDTO.color(), socksSearchCriteriaResult.color()),
                    () -> assertEquals(ComparisonOperator.LESS_THAN, socksSearchCriteriaResult.comparisonOperator()),
                    () -> assertEquals(socksSearchCriteriaDTO.cottonPercentage(), socksSearchCriteriaResult.cottonPercentage()));
        }
    }

    @Test
    @DisplayName("dtoToSocksSearchCriteria should throw MappingException " +
            "if validation component throws ValidationException")
    public void dtoToSocksSearchCriteriaNegativeTestCase1() {
        doThrow(ValidationException.class).when(dtoMapperValidator)
                .validateSocksSearchCriteriaDTO(nullable(SocksSearchCriteriaDTO.class));

        assertThrows(MappingException.class, () ->
                dtoMapper.dtoToSocksSearchCriteria(null));
    }

    @Test
    @DisplayName("dtoToSocksSearchCriteria should throw MappingException " +
            "if comparison operator mapping result is null (if it is not null)")
    public void dtoToSocksSearchCriteriaNegativeTestCase2() {
        SocksSearchCriteriaDTO socksSearchCriteriaDTO =
                new SocksSearchCriteriaDTO("red", "1234", 50);

        try (MockedStatic<ComparisonOperator> comparisonOperatorMockedStatic = mockStatic(ComparisonOperator.class)) {
            comparisonOperatorMockedStatic.when(() -> ComparisonOperator.getByName(any(String.class)))
                    .thenReturn(null);
            assertThrows(MappingException.class, () ->
                    dtoMapper.dtoToSocksSearchCriteria(socksSearchCriteriaDTO));
        }
    }

    @Test
    @DisplayName("socksToSearchCriteriaDto should return SocksSearchCriteriaDTO with SocksSearchCriteria data " +
            "if validation component doesn't throws ValidationException")
    public void socksToSearchCriteriaDtoPositiveTestCase() {
        SocksSearchCriteria socksSearchCriteria =
                new SocksSearchCriteria("red", ComparisonOperator.LESS_THAN, 50);
        SocksSearchCriteriaDTO socksSearchCriteriaDTOResult = dtoMapper.socksToSearchCriteriaDto(socksSearchCriteria);

        assertAll(
                () -> assertEquals(socksSearchCriteria.color(), socksSearchCriteriaDTOResult.color()),
                () -> assertEquals("lessThan", socksSearchCriteriaDTOResult.comparisonOperator()),
                () -> assertEquals(socksSearchCriteria.cottonPercentage(), socksSearchCriteriaDTOResult.cottonPercentage()));
    }

    @Test
    @DisplayName("socksToSearchCriteriaDto should throw MappingException " +
            "if validation component throws ValidationException")
    public void socksToSearchCriteriaDtoNegativeTestCase() {
        doThrow(ValidationException.class).when(dtoMapperValidator)
                .validateSocksSearchCriteria(nullable(SocksSearchCriteria.class));

        assertThrows(MappingException.class, () ->
                dtoMapper.socksToSearchCriteriaDto(null));
    }
}
