package ar.org.example.createpdf.model;

public class Product {

    private String id;
    private String name;
    private String value;
    private String count;

    public Product(String id, String name, String value, String count) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
