package sample;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product {

    private String name;
    private String category;
    private Float price;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean getAvailableness() {
        return availableness;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public void setAvailableness(boolean availableness) {
        this.availableness = availableness;
    }
}
