package model;

import connection.ConfigurationDB;
import entity.Client;
import entity.Product;
import entity.Purchase;
import entity.Store;
import repository.CrudRepository;
import service.PurchaseService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PurchaseModel implements CrudRepository {
    Connection objConnection;

    @Override
    public Object save(Object object) {
        PurchaseService purchaseService = new PurchaseService();
        Purchase purchase = (Purchase) object;
        int currentStock = purchaseService.currentStock(purchase.getQuantity(), purchase.getProductId(), 0);
        if (currentStock < 0) {
            JOptionPane.showMessageDialog(null, "We are sorry, we don't have that many products in stock.");
            return null;
        }

        objConnection = ConfigurationDB.openConnection();
        try {
            String sql = "INSERT INTO purchase (client_id, product_id, quantity) VALUES ( ?, ?, ?);";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setInt(1, purchase.getClientId());
            statement.setInt(2, purchase.getProductId());
            statement.setInt(3, purchase.getQuantity());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            while (rs.next()) {
                purchase.setId(rs.getInt(1));
            }
            JOptionPane.showMessageDialog(null, "Purchase insertion completed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        purchaseService.updateStock(currentStock, purchase.getProductId());
        purchaseService.showPurchase(purchase);
        ConfigurationDB.closeConnection();
        return purchase;
    }

    @Override
    public void update(Object object) {
        PurchaseService purchaseService = new PurchaseService();
        Purchase purchase = (Purchase) object;
        int currentStock = purchaseService.currentStock(purchase.getQuantity(), purchase.getProductId(), purchase.getId());
        if (currentStock >= 0) {
            objConnection = ConfigurationDB.openConnection();
            try {
                String sql = "UPDATE purchase SET client_id = ?, product_id = ?, quantity = ? WHERE id = ?;";
                PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
                statement.setInt(1, purchase.getClientId());
                statement.setInt(2, purchase.getProductId());
                statement.setInt(3, purchase.getQuantity());
                statement.setInt(4, purchase.getId());

                if (statement.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Purchase updating completed successfully");
                }
            } catch (SQLException e) {
                ConfigurationDB.closeConnection();
                throw new RuntimeException(e);
            }

            purchaseService.updateStock(currentStock, purchase.getProductId());
            purchaseService.showPurchase(purchase);
            ConfigurationDB.closeConnection();
        } else JOptionPane.showMessageDialog(null, "We are sorry, we don't have that many products in stock.");
    }

    @Override
    public void delete(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Purchase purchase = (Purchase) object;
        try {
            String sql = "DELETE FROM purchase WHERE id =?";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, purchase.getId());
            statement.execute();

            if (statement.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Purchase deleting completed successfully");
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
    }

    @Override
    public Object find(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Purchase purchase = new Purchase();
        try {
            String sql = "SELECT * FROM purchase INNER JOIN client ON purchase.client_id = client.id INNER JOIN product ON product.id = purchase.product_id INNER JOIN store ON store.id = product.store_id WHERE purchase.id = ?;";

            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, (int) object);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Client client = new Client();
            Product product = new Product();
            Store store = new Store();

            client.setId(resultSet.getInt("client.id"));
            client.setName(resultSet.getString("client.name"));
            client.setLastname(resultSet.getString("client.lastname"));
            client.setEmail(resultSet.getString("client.email"));

            product.setId(resultSet.getInt("product.id"));
            product.setStoreId(resultSet.getInt("product.store_id"));
            product.setPrice(resultSet.getDouble("product.price"));
            product.setName(resultSet.getString("product.name"));

            store.setId(resultSet.getInt("store.id"));
            store.setName(resultSet.getString("store.name"));
            store.setLocation(resultSet.getString("store.location"));

            product.setStock(resultSet.getInt("product.stock"));
            purchase.setId(resultSet.getInt("purchase.id"));
            purchase.setClientId(resultSet.getInt("purchase.client_id"));
            purchase.setProductId(resultSet.getInt("purchase.product_id"));
            purchase.setQuantity(resultSet.getInt("purchase.quantity"));
            purchase.setPurchaseDate(resultSet.getDate("purchase.purchase_date"));

            product.setStore(store);
            purchase.setClient(client);
            purchase.setProduct(product);

        } catch (Exception e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return purchase;
    }

    @Override
    public List<Object> findAll() {
        objConnection = ConfigurationDB.openConnection();
        List<Purchase> purchases = new ArrayList<>();
        try {
            String sql = "SELECT * FROM purchase INNER JOIN client ON purchase.client_id = client.id INNER JOIN product ON product.id = purchase.product_id INNER JOIN store ON store.id = product.store_id;";
            try (PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    Product product = new Product();
                    Purchase purchase = new Purchase();
                    Store store = new Store();

                    client.setId(resultSet.getInt("client.id"));
                    client.setName(resultSet.getString("client.name"));
                    client.setLastname(resultSet.getString("client.lastname"));
                    client.setEmail(resultSet.getString("client.email"));

                    product.setId(resultSet.getInt("product.id"));
                    product.setStoreId(resultSet.getInt("product.store_id"));
                    product.setPrice(resultSet.getDouble("product.price"));
                    product.setName(resultSet.getString("product.name"));

                    store.setId(resultSet.getInt("store.id"));
                    store.setName(resultSet.getString("store.name"));
                    store.setLocation(resultSet.getString("store.location"));

                    product.setStock(resultSet.getInt("product.stock"));
                    purchase.setId(resultSet.getInt("purchase.id"));
                    purchase.setClientId(resultSet.getInt("purchase.client_id"));
                    purchase.setProductId(resultSet.getInt("purchase.product_id"));
                    purchase.setQuantity(resultSet.getInt("purchase.quantity"));
                    purchase.setPurchaseDate(resultSet.getDate("purchase.purchase_date"));

                    product.setStore(store);
                    purchase.setClient(client);
                    purchase.setProduct(product);

                    purchases.add(purchase);
                }
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        ConfigurationDB.closeConnection();
        return Collections.singletonList(purchases);
    }

    public List<Object> findAllBySomething(int id) {
        objConnection = ConfigurationDB.openConnection();
        List<Purchase> purchases = new ArrayList<>();
        try {
            String sql = "SELECT * FROM purchase INNER JOIN client ON purchase.client_id = client.id INNER JOIN product ON product.id = purchase.product_id INNER JOIN store ON store.id = product.store_id where product.id = " + id + ";";
            try (PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    Product product = new Product();
                    Purchase purchase = new Purchase();
                    Store store = new Store();

                    client.setId(resultSet.getInt("client.id"));
                    client.setName(resultSet.getString("client.name"));
                    client.setLastname(resultSet.getString("client.lastname"));
                    client.setEmail(resultSet.getString("client.email"));

                    product.setId(resultSet.getInt("product.id"));
                    product.setStoreId(resultSet.getInt("product.store_id"));
                    product.setPrice(resultSet.getDouble("product.price"));
                    product.setName(resultSet.getString("product.name"));

                    store.setId(resultSet.getInt("store.id"));
                    store.setName(resultSet.getString("store.name"));
                    store.setLocation(resultSet.getString("store.location"));

                    product.setStock(resultSet.getInt("product.stock"));
                    purchase.setId(resultSet.getInt("purchase.id"));
                    purchase.setClientId(resultSet.getInt("purchase.client_id"));
                    purchase.setProductId(resultSet.getInt("purchase.product_id"));
                    purchase.setQuantity(resultSet.getInt("purchase.quantity"));
                    purchase.setPurchaseDate(resultSet.getDate("purchase.purchase_date"));

                    product.setStore(store);
                    purchase.setClient(client);
                    purchase.setProduct(product);

                    purchases.add(purchase);
                }
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        ConfigurationDB.closeConnection();
        return Collections.singletonList(purchases);
    }
}
