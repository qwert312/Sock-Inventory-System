package com.daniil.sockinventorysystem.services;

import com.daniil.sockinventorysystem.domain.ComparisonOperator;
import com.daniil.sockinventorysystem.domain.SocksSearchCriteria;
import com.daniil.sockinventorysystem.domain.entities.Socks;
import com.daniil.sockinventorysystem.exceptions.CSVFileProcessingException;
import com.daniil.sockinventorysystem.exceptions.NotEnoughSocksException;
import com.daniil.sockinventorysystem.exceptions.SocksDoesNotExistsException;
import com.daniil.sockinventorysystem.repositories.SocksRepository;
import com.daniil.sockinventorysystem.validators.ServiceValidator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class SocksService {

    private SocksRepository socksRepository;
    private ServiceValidator serviceValidator;

    public SocksService(SocksRepository socksRepository, ServiceValidator serviceValidator) {
        this.socksRepository = socksRepository;
        this.serviceValidator = serviceValidator;
    }

    public int getSocksQuantity(SocksSearchCriteria socksSearchCriteria) {
        serviceValidator.validateSocksSearchCriteria(socksSearchCriteria);

        String color = socksSearchCriteria.color();
        ComparisonOperator comparisonOperator = socksSearchCriteria.comparisonOperator();
        Integer cottonPercentage = socksSearchCriteria.cottonPercentage();

        int quantity = 0;

        if (color != null && comparisonOperator != null && cottonPercentage != null) {
            if (comparisonOperator == ComparisonOperator.MORE_THAN)
                quantity = socksRepository.
                                getSocksQuantityByColorAndCottonPercentageMoreThan(color, cottonPercentage).
                                orElse(0);
            else if (comparisonOperator == ComparisonOperator.LESS_THAN)
                quantity = socksRepository.
                                getSocksQuantityByColorAndCottonPercentageLessThan(color, cottonPercentage).
                                orElse(0);
            else
                quantity = socksRepository.
                                getSocksQuantityByColorAndCottonPercentageEquals(color, cottonPercentage).
                                orElse(0);
        } else if (color != null) {
            quantity = socksRepository.
                    getSocksQuantityByColor(color).
                    orElse(0);
        } else if (comparisonOperator != null && cottonPercentage != null) {
            if (comparisonOperator == ComparisonOperator.MORE_THAN)
                quantity = socksRepository.
                        getSocksQuantityByCottonPercentageMoreThan(cottonPercentage).
                        orElse(0);
            else if (comparisonOperator == ComparisonOperator.LESS_THAN)
                quantity = socksRepository.
                        getSocksQuantityByCottonPercentageLessThan(cottonPercentage).
                        orElse(0);
             else
                quantity = socksRepository.
                        getSocksQuantityByCottonPercentageEquals(cottonPercentage).
                        orElse(0);
        } else {
            quantity = socksRepository.
                    getSocksQuantity().
                    orElse(0);
        }

        return quantity;
    }

    public void addOrIncreaseSocksQuantity(Socks socks) {
        serviceValidator.validateSocks(socks);

        Optional<Socks> foundSocksOptional =
                socksRepository.findByColorAndCottonPercentage(socks.getColor(), socks.getCottonPercentage());

        foundSocksOptional.ifPresentOrElse(
                foundSocks -> {
                    foundSocks.setQuantity(foundSocks.getQuantity() + socks.getQuantity());
                    socksRepository.save(foundSocks);
                },
                () -> socksRepository.save(socks));
    }

    public void decreaseSocksQuantity(Socks socks) {
        serviceValidator.validateSocks(socks);

        Optional<Socks> foundSocksOptional =
                socksRepository.findByColorAndCottonPercentage(socks.getColor(), socks.getCottonPercentage());

        Socks foundSocks = foundSocksOptional.orElseThrow(
                () -> new SocksDoesNotExistsException(
                        String.format("There is no socks with this color and cotton percentage: %s, %s.",
                        socks.getColor(), socks.getCottonPercentage())));

        if (foundSocks.getQuantity() - socks.getQuantity() < 0)
            throw new NotEnoughSocksException("Not enough socks for decrease.");
        else {
            foundSocks.setQuantity(foundSocks.getQuantity() - socks.getQuantity());
            socksRepository.save(foundSocks);
        }
    }

    public void update(long id, Socks socks) {
        serviceValidator.validateSocks(socks);

        Optional<Socks> foundSocksOptional = socksRepository.findById(id);

        Socks foundSocks = foundSocksOptional.orElseThrow(
                () -> new SocksDoesNotExistsException(String.format("There is no socks with this id: %s.", id)));

        foundSocks.setColor(socks.getColor());
        foundSocks.setCottonPercentage(socks.getCottonPercentage());
        foundSocks.setQuantity(socks.getQuantity());
        socksRepository.save(foundSocks);
    }

    @Transactional
    public void addOrIncreaseSocksQuantityFromCSV(MultipartFile csvFile) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile.getInputStream()))) {
            String[] line = {""};
            try {
                while ((line = csvReader.readNext()) != null) {
                    String color = line[0];
                    int cottonPercentage = Integer.parseInt(line[1]);
                    int quantity = Integer.parseInt(line[2]);
                    Socks socks = new Socks(color, cottonPercentage, quantity);
                    addOrIncreaseSocksQuantity(socks);
                }
            } catch (CsvValidationException | NumberFormatException | ArrayIndexOutOfBoundsException processingException) {
                throw new CSVFileProcessingException(String.format(
                        "File has incorrect format. Line: %s. " +
                                "Expected CSV file with exactly three values per line: string, integer, integer.",
                                String.join(",", line)));
            }
        } catch (IOException ioException) {
            throw new CSVFileProcessingException("Unexpected error.");
        }
    }
}
