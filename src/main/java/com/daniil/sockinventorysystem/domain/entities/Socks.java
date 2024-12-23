package com.daniil.sockinventorysystem.domain.entities;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"color", "cotton_percentage"}))
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false, name = "cotton_percentage",
            columnDefinition = "INT CHECK (cotton_percentage BETWEEN 0 AND 100)")
    private int cottonPercentage;

    @Column(nullable = false, columnDefinition = "INT CHECK (quantity >= 0)")
    private int quantity;

    public Socks() {
    }

    public Socks(String color, int cottonPercentage, int quantity) {
        this.color = color;
        this.cottonPercentage = cottonPercentage;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCottonPercentage() {
        return cottonPercentage;
    }

    public void setCottonPercentage(int cottonPercentage) {
        this.cottonPercentage = cottonPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "Socks[id = %s, color = %s, cottonPercentage = %s, quantity = %s]",
                id, color, cottonPercentage, quantity);
    }
}
