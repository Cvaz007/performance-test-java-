package entity;

public class Product {
    private int id;
    private String name;
    private double price;
    private int storeId;
    private Store store;
    private int stock;

    public Product() {
    }

    public Product(int id, String name, double price, int storeId, Store store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storeId = storeId;
        this.store = store;
    }

    public Product(int id, String name, double price, int storeId, Store store, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.storeId = storeId;
        this.store = store;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", storeId=" + storeId +
                ", store=" + store.toString() +
                ", stock=" + stock +
                '}'+"\n";
    }
}
