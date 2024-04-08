package entity;

import java.util.Date;

public class Purchase {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;
    private Date purchaseDate;
    private Product product;
    private Client client;

    public Purchase() {
    }

    public Purchase(int id, int clientId, int productId, int quantity, Date purchaseDate) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int id, int clientId, int productId, int quantity, Date purchaseDate, Product product, Client client) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.product = product;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "\nPurchase{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", purchaseDate=" + purchaseDate +
                ", product=" + product.toString() +
                ", client=" + client.toString() +
                '}'+"\n";
    }
}
