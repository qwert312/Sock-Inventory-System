package com.daniil.sockinventorysystem.repositories;

import com.daniil.sockinventorysystem.domain.entities.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks, Long> {

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color AND s.cottonPercentage > :cottonPercentage")
    Optional<Integer> getSocksQuantityByColorAndCottonPercentageMoreThan(String color, int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color AND s.cottonPercentage < :cottonPercentage")
    Optional<Integer> getSocksQuantityByColorAndCottonPercentageLessThan(String color, int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color AND s.cottonPercentage = :cottonPercentage")
    Optional<Integer> getSocksQuantityByColorAndCottonPercentageEquals(String color, int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color")
    Optional<Integer> getSocksQuantityByColor(String color);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.cottonPercentage > :cottonPercentage")
    Optional<Integer> getSocksQuantityByCottonPercentageMoreThan(int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.cottonPercentage < :cottonPercentage")
    Optional<Integer> getSocksQuantityByCottonPercentageLessThan(int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.cottonPercentage = :cottonPercentage")
    Optional<Integer> getSocksQuantityByCottonPercentageEquals(int cottonPercentage);

    @Query("SELECT SUM(s.quantity) FROM Socks s")
    Optional<Integer> getSocksQuantity();

    @Query("SELECT s FROM Socks s WHERE s.color = :color AND s.cottonPercentage = :cottonPercentage")
    Optional<Socks> findByColorAndCottonPercentage(String color, int cottonPercentage);
}
