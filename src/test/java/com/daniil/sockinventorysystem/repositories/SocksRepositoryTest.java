package com.daniil.sockinventorysystem.repositories;

import com.daniil.sockinventorysystem.domain.entities.Socks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SocksRepositoryTest {

    @Autowired
    private SocksRepository socksRepository;

    @Test
    @DisplayName("getSocksQuantityByColorAndCottonPercentageMoreThan should return quantity " +
            "by provided filters")
    public void getSocksQuantityByColorAndCottonPercentageMoreThanTestCase() {
        Socks socksForSave1 = new Socks("red", 65, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(20,
                socksRepository.getSocksQuantityByColorAndCottonPercentageMoreThan("red", 60).get());
    }

    @Test
    @DisplayName("getSocksQuantityByColorAndCottonPercentageLessThan should return quantity " +
            "by provided filters")
    public void getSocksQuantityByColorAndCottonPercentageLessThanTestCase() {
        Socks socksForSave1 = new Socks("red", 65, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(20,
                socksRepository.getSocksQuantityByColorAndCottonPercentageLessThan("red", 68).get());
    }

    @Test
    @DisplayName("getSocksQuantityByColorAndCottonPercentageEquals should return quantity " +
            "by provided filters")
    public void getSocksQuantityByColorAndCottonPercentageEqualsTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(10,
                socksRepository.getSocksQuantityByColorAndCottonPercentageEquals("red", 50).get());
    }

    @Test
    @DisplayName("getSocksQuantityByColor should return quantity " +
            "by provided filters")
    public void getSocksQuantityByColorTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(30,
                socksRepository.getSocksQuantityByColor("red").get());
    }

    @Test
    @DisplayName("getSocksQuantityByCottonPercentageMoreThan should return quantity " +
            "by provided filters")
    public void getSocksQuantityByCottonPercentageMoreThanTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 70, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(20,
                socksRepository.getSocksQuantityByCottonPercentageMoreThan(65).get());
    }

    @Test
    @DisplayName("getSocksQuantityByCottonPercentageLessThan should return quantity " +
            "by provided filters")
    public void getSocksQuantityByCottonPercentageLessThanTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(20,
                socksRepository.getSocksQuantityByCottonPercentageLessThan(55).get());
    }

    @Test
    @DisplayName("getSocksQuantityByCottonPercentageEquals should return quantity " +
            "by provided filters")
    public void getSocksQuantityByCottonPercentageEqualsTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(20,
                socksRepository.getSocksQuantityByCottonPercentageLessThan(55).get());
    }

    @Test
    @DisplayName("getSocksQuantity should return total quantity")
    public void getSocksQuantityTestCase() {
        Socks socksForSave1 = new Socks("red", 60, 10);
        Socks socksForSave2 = new Socks("red", 70, 10);
        Socks socksForSave3 = new Socks("red", 50, 10);
        Socks socksForSave4 = new Socks("blue", 50, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3, socksForSave4));

        assertEquals(40,
                socksRepository.getSocksQuantity().get());
    }

    @Test
    @DisplayName("findByColorAndCottonPercentage ")
    public void findByColorAndCottonPercentagePositiveTestCase() {
        Socks socksForSave1 = new Socks("yellow", 60, 10);
        Socks socksForSave2 = new Socks("blue", 70, 10);
        Socks socksForSave3 = new Socks("yellow", 70, 10);
        socksRepository.saveAll(List.of(socksForSave1, socksForSave2, socksForSave3));

        Socks foundSocks = socksRepository.findByColorAndCottonPercentage("yellow", 60).get();
        assertAll(
                () -> assertEquals("yellow", foundSocks.getColor()),
                () -> assertEquals(60, foundSocks.getCottonPercentage()),
                () -> assertEquals(10, foundSocks.getQuantity()));
    }
}
