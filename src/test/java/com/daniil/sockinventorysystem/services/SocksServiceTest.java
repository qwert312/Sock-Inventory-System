package com.daniil.sockinventorysystem.services;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.exceptions.CSVFileProcessingException;
import com.daniil.sockinventorysystem.exceptions.NotEnoughSocksException;
import com.daniil.sockinventorysystem.exceptions.SocksDoesNotExistsException;
import com.daniil.sockinventorysystem.exceptions.ValidationException;
import com.daniil.sockinventorysystem.repositories.SocksRepository;
import com.daniil.sockinventorysystem.validators.ServiceValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocksServiceTest {

    @Mock
    ServiceValidator serviceValidator;

    @Mock
    SocksRepository socksRepository;

    @Spy
    @InjectMocks
    SocksService socksService;

    @Test
    @DisplayName("getSocksQuantity should return quantity based on specified " +
            "color, comparison operator and cotton percentage, if they are not null")
    public void getSocksQuantityPositiveTestCase1() {
        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", ComparisonOperator.MORE_THAN, 25));
        verify(socksRepository).getSocksQuantityByColorAndCottonPercentageMoreThan(anyString(), anyInt());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", ComparisonOperator.LESS_THAN, 25));
        verify(socksRepository).getSocksQuantityByColorAndCottonPercentageLessThan(anyString(), anyInt());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", ComparisonOperator.EQUALS, 25));
        verify(socksRepository).getSocksQuantityByColorAndCottonPercentageEquals(anyString(), anyInt());
    }

    @Test
    @DisplayName("getSocksQuantity should return quantity based only on specified " +
            "color if comparison operator or cotton percentage are null")
    public void getSocksQuantityPositiveTestCase2() {
        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", null, null));
        verify(socksRepository, times(1)).getSocksQuantityByColor(anyString());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", ComparisonOperator.EQUALS, null));
        verify(socksRepository, times(2)).getSocksQuantityByColor(anyString());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                "red", null, 25));
        verify(socksRepository, times(3)).getSocksQuantityByColor(anyString());
    }

    @Test
    @DisplayName("getSocksQuantity should return quantity based on specified " +
            "comparison operator and cotton percentage, if color is null")
    public void getSocksQuantityPositiveTestCase3() {
        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, ComparisonOperator.MORE_THAN, 25));
        verify(socksRepository).getSocksQuantityByCottonPercentageMoreThan(anyInt());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, ComparisonOperator.LESS_THAN, 25));
        verify(socksRepository).getSocksQuantityByCottonPercentageLessThan(anyInt());

        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, ComparisonOperator.EQUALS, 25));
        verify(socksRepository).getSocksQuantityByCottonPercentageEquals(anyInt());
    }

    @Test
    @DisplayName("getSocksQuantity should return total quantity " +
            "if the color is null and either the cotton percentage or the comparison operator is null")
    public void getSocksQuantityPositiveTestCase4() {
        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, ComparisonOperator.MORE_THAN, null));
        verify(socksRepository, times(1)).getSocksQuantity();

        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, null, 25));
        verify(socksRepository, times(2)).getSocksQuantity();
    }

    @Test
    @DisplayName("getSocksQuantity should return total quantity " +
            "if color, cotton percentage and comparison operator are null")
    public void getSocksQuantityPositiveTestCase5() {
        socksService.getSocksQuantity(new SocksSearchCriteria(
                null, null, null));
        verify(socksRepository).getSocksQuantity();
    }

    @Test
    @DisplayName("getSocksQuantity should throw ValidationException if validation component throws it")
    public void getSocksQuantityNegativeTestCase() {
        doThrow(ValidationException.class).when(serviceValidator)
                .validateSocksSearchCriteria(nullable(SocksSearchCriteria.class));

        assertThrows(ValidationException.class, () -> socksService.getSocksQuantity(null));
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantity should add socks if they are not exists")
    public void addOrIncreaseSocksQuantityPositiveTestCase1() {
        Socks parameterSocks = mock(Socks.class);
        when(parameterSocks.getColor()).thenReturn("red");
        when(parameterSocks.getCottonPercentage()).thenReturn(50);
        when(socksRepository.findByColorAndCottonPercentage(anyString(), anyInt()))
                .thenReturn(Optional.empty());

        socksService.addOrIncreaseSocksQuantity(parameterSocks);
        verify(socksRepository).save(parameterSocks);
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantity should increase socks quantity if they are exists")
    public void addOrIncreaseSocksQuantityPositiveTestCase2() {
        Socks foundSocks = mock(Socks.class);
        when(socksRepository.findByColorAndCottonPercentage(anyString(), anyInt()))
                .thenReturn(Optional.of(foundSocks));

        socksService.addOrIncreaseSocksQuantity(new Socks("red", 12, 15));
        verify(foundSocks).setQuantity(anyInt());
        verify(socksRepository).save(foundSocks);
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantity should throw ValidationException if validation component throws it")
    public void addOrIncreaseSocksQuantityNegativeTestCase() {
        doThrow(ValidationException.class).when(serviceValidator)
                .validateSocks(nullable(Socks.class));

        assertThrows(ValidationException.class, () -> socksService.addOrIncreaseSocksQuantity(null));
    }

    @Test
    @DisplayName("decreaseSocksQuantity should decrease socks quantity if they exist" +
            "and the provided quantity is less than existing socks quantity")
    public void decreaseSocksQuantityPositiveTestCase() {
        Socks foundSocks = mock(Socks.class);
        Socks socksParameter = new Socks("red", 50, 10);
        when(foundSocks.getQuantity()).thenReturn(15);
        when(socksRepository.findByColorAndCottonPercentage(anyString(), anyInt()))
                .thenReturn(Optional.of(foundSocks));

        socksService.decreaseSocksQuantity(socksParameter);
        int resultQuantity = foundSocks.getQuantity() - socksParameter.getQuantity();
        verify(foundSocks).setQuantity(resultQuantity);
        verify(socksRepository).save(foundSocks);
    }

    @Test
    @DisplayName("decreaseSocksQuantity should throw ValidationException if validation component throws it")
    public void decreaseSocksQuantityNegativeTestCase1() {
        doThrow(ValidationException.class).when(serviceValidator)
                .validateSocks(nullable(Socks.class));

        assertThrows(ValidationException.class, () -> socksService.addOrIncreaseSocksQuantity(null));
    }

    @Test
    @DisplayName("decreaseSocksQuantity should throw SocksDoesNotExistsException " +
            "if socks with provided color and cotton percentage do not exist")
    public void decreaseSocksQuantityNegativeTestCase2() {
        when(socksRepository.findByColorAndCottonPercentage(anyString(), anyInt()))
                .thenReturn(Optional.empty());

        assertThrows(SocksDoesNotExistsException.class,
                () -> socksService.decreaseSocksQuantity(new Socks("red", 10, 15)));
    }

    @Test
    @DisplayName("decreaseSocksQuantity should throw NotEnoughSocksException if Socks exist, but" +
            "provided quantity is greater than existing socks quantity")
    public void decreaseSocksQuantityNegativeTestCase3() {
        Socks foundSocks = mock(Socks.class);
        when(foundSocks.getQuantity()).thenReturn(10);
        when(socksRepository.findByColorAndCottonPercentage(anyString(), anyInt()))
                .thenReturn(Optional.of(foundSocks));

        assertThrows(NotEnoughSocksException.class, () ->
                socksService.decreaseSocksQuantity(new Socks("red", 50, 15)));
    }

    @Test
    @DisplayName("update should update fields of found socks with values from provided socks " +
            "if socks are found")
    public void updatePositiveTestCase() {
        Socks foundSocks = mock(Socks.class);
        Socks parameterSocks = new Socks("red", 10, 25);
        when(socksRepository.findById(anyLong())).thenReturn(Optional.of(foundSocks));

        socksService.update(1, parameterSocks);
        verify(foundSocks).setColor(parameterSocks.getColor());
        verify(foundSocks).setCottonPercentage(parameterSocks.getCottonPercentage());
        verify(foundSocks).setQuantity(parameterSocks.getQuantity());
        verify(socksRepository).save(foundSocks);
    }

    @Test
    @DisplayName("update should throw ValidationException if validation component throws it")
    public void updateNegativeTestCase1() {
        doThrow(ValidationException.class).when(serviceValidator)
                .validateSocks(nullable(Socks.class));

        assertThrows(ValidationException.class, () -> socksService.addOrIncreaseSocksQuantity(null));
    }

    @Test
    @DisplayName("update should throw SocksDoestNotExists if socks does not exists")
    public void updateNegativeTestCase2() {
        when(socksRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SocksDoesNotExistsException.class,
                () -> socksService.update(1, new Socks("red", 1, 2)));
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantityFromCSV should apply addOrIncreaseSocksQuantity logic for every " +
            "line in provided file, if it has valid CSV format with fields: string, int, int per line")
    public void addOrIncreaseSocksQuantityFromCSVPositiveTestCase() throws IOException {
        MultipartFile csvFile = mock(MultipartFile.class);
        String csvContent = "red,100,50\nblue,80,30\nred,100,10";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        when(csvFile.getInputStream()).thenReturn(inputStream);
        doNothing().when(socksService).addOrIncreaseSocksQuantity(any(Socks.class));

        socksService.addOrIncreaseSocksQuantityFromCSV(csvFile);
        verify(socksService, times(3)).addOrIncreaseSocksQuantity(any(Socks.class));
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantityFromCSV should throw ValidationException " +
            "if any line fails validation by addOrIncreaseSocksQuantity logic")
    public void addOrIncreaseSocksQuantityFromCSVNegativeTestCase1() throws IOException {
        MultipartFile csvFile = mock(MultipartFile.class);
        String csvContent = "red,101,50\nblue,80,30\nred,100,10";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        when(csvFile.getInputStream()).thenReturn(inputStream);
        doThrow(ValidationException.class).when(socksService).addOrIncreaseSocksQuantity(any(Socks.class));

        assertThrows(ValidationException.class, () -> socksService.addOrIncreaseSocksQuantityFromCSV(csvFile));
    }

    @Test
    @DisplayName("addOrIncreaseSocksQuantityFromCSV should throw CSVFileProcessingException " +
            "if file has incorrect format")
    public void addOrIncreaseSocksQuantityFromCSVNegativeTestCase2() throws IOException {
        MultipartFile csvFile = mock(MultipartFile.class);
        String csvContent = "red,101,50\nblue,30\nred,100,10";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        when(csvFile.getInputStream()).thenReturn(inputStream);
        doNothing().when(socksService).addOrIncreaseSocksQuantity(any(Socks.class));

        assertThrows(CSVFileProcessingException.class,
                () -> socksService.addOrIncreaseSocksQuantityFromCSV(csvFile));

        csvContent = "red,red,50\nblue,30,10\nred,100,10";
        inputStream = new ByteArrayInputStream(csvContent.getBytes());
        when(csvFile.getInputStream()).thenReturn(inputStream);

        assertThrows(CSVFileProcessingException.class,
                () -> socksService.addOrIncreaseSocksQuantityFromCSV(csvFile));
    }
}
