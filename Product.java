package sample;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product {

    private String name;
    private String category;
    private int price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
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

    public void setAvailableness(String availableness) {
        this.availableness = availableness.equals("true") | availableness.equals("True");

    }
}
