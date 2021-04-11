package sample;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private String name;
    private String category;
    private BigDecimal price;
    private String description;
    private boolean availableness;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailableness() {
        return availableness;
    }

    public void setAvailableness(boolean availableness) {
        this.availableness = availableness;
    }
}
