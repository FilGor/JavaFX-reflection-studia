package sample;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private String name;
    private String category;
    private BigDecimal price;
    private boolean availableness;
    private String textDescription;


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

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public boolean isAvailableness() {
        return availableness;
    }

    public void setAvailableness(boolean availableness) {
        this.availableness = availableness;
    }
}
