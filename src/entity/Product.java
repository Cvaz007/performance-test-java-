package entity;

public class Product {
    private int id;
    private String name;
    private double price;
    private int storeId;
    private Store store;

    public Product() {
    }

    public Product(int id, String name, double price, int storeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storeId = storeId;
    }

    public Product(int id, String name, double price, int storeId, Store store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storeId = storeId;
        this.store = store;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", storeId=" + storeId +
                ", store=" + store.toString() +
                '}';
    }
}
