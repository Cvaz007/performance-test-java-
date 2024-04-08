package model;

import connection.ConfigurationDB;
import entity.Client;
import entity.Product;
import entity.Store;
import repository.CrudRepository;
import repository.GetByStringRepository;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductModel implements CrudRepository, GetByStringRepository {
    Connection objConnection;

    @Override
    public Object save(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Product product = (Product) object;
        try {
            String sql = "INSERT INTO product ( name, price, stock, store_id) VALUES ( ?, ?, ?, ?);";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setInt(4, product.getStoreId());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            while (rs.next()) {
                product.setId(rs.getInt(1));
            }
            JOptionPane.showMessageDialog(null, "Product insertion completed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return product;
    }

    @Override
    public void update(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Product product = (Product) object;
        try {
            String sql = "UPDATE product SET name = ?, price = ?, stock = ?, store_id = ? WHERE id = ?;";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setInt(4, product.getStoreId());
            statement.setInt(5, product.getId());

            if (statement.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Product updating completed successfully");
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
    }

    @Override
    public void delete(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Product product = (Product) object;
        try {
            String sql = "DELETE FROM product WHERE id =?";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, product.getId());
            statement.execute();

            if (statement.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Product deleting completed successfully");
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
        Product product = new Product();
        try {
            String sql = "SELECT * FROM product INNER JOIN store ON store.id = product.store_id WHERE product.id = ?;";

            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1,(int) object);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Store store = new Store();
            store.setId(resultSet.getInt("store.id"));
            store.setName(resultSet.getString("store.name"));
            store.setLocation(resultSet.getString("store.location"));

            product.setStock(resultSet.getInt("product.stock"));
            product.setId(resultSet.getInt("product.id"));
            product.setStoreId(resultSet.getInt("product.store_id"));
            product.setPrice(resultSet.getDouble("product.price"));
            product.setName(resultSet.getString("product.name"));
            product.setStore(store);

        } catch (Exception e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return product;
    }

    @Override
    public List<Object> findAll() {
        objConnection = ConfigurationDB.openConnection();
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM product INNER JOIN store ON store.id = product.store_id";
            try (PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    Store store = new Store();

                    store.setId(resultSet.getInt("store.id"));
                    store.setName(resultSet.getString("store.name"));
                    store.setLocation(resultSet.getString("store.location"));

                    product.setStock(resultSet.getInt("product.stock"));
                    product.setId(resultSet.getInt("product.id"));
                    product.setStoreId(resultSet.getInt("product.store_id"));
                    product.setPrice(resultSet.getDouble("product.price"));
                    product.setName(resultSet.getString("product.name"));
                    product.setStore(store);

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        ConfigurationDB.closeConnection();
        return Collections.singletonList(products);
    }

    @Override
    public Object find(String string) {
        objConnection = ConfigurationDB.openConnection();
        Product product = new Product();
        try {
            String sql = "SELECT * FROM product INNER JOIN store ON store.id = product.store_id WHERE product.name like ? OR store.name like ?;";

            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setString(1, "%" + string + "%");
            statement.setString(2, "%" + string + "%");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Store store = new Store();
            store.setId(resultSet.getInt("store.id"));
            store.setName(resultSet.getString("store.name"));
            store.setLocation(resultSet.getString("store.location"));

            product.setStock(resultSet.getInt("product.stock"));
            product.setId(resultSet.getInt("product.id"));
            product.setStoreId(resultSet.getInt("product.store_id"));
            product.setPrice(resultSet.getDouble("product.price"));
            product.setName(resultSet.getString("product.name"));
            product.setStore(store);

        } catch (Exception e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return product;
    }

    public void updateStock(int stock, int id) {
        objConnection = ConfigurationDB.openConnection();
        try {
            String sql = "UPDATE product SET  stock = ? WHERE id = ?;";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, stock);
            statement.setInt(2, id);

            if (statement.executeUpdate() != 0) {
                System.out.println("Product update successful");
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
    }
}
