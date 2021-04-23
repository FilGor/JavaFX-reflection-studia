package sample;

public class Product {

    private String name = "DefualtName";
    private String category = "SomeCategory";
    private Float price = Float.parseFloat("2.22");
    private boolean availableness = false;
    private String textDescription = "some random description";


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
