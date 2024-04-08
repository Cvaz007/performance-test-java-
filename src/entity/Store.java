package entity;

public class Store {
    private int id;
    private String name;
    private String location;
    private int stock;

    public Store() {
    }

    public Store(int id, String name, String location, int stock) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", stock=" + stock +
                '}';
    }
}
