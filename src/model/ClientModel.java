package model;

import connection.ConfigurationDB;
import entity.Client;
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

public class ClientModel implements CrudRepository, GetByStringRepository {
    Connection objConnection;

    @Override
    public Object save(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Client client = (Client) object;
        try {
            String sql = "INSERT INTO client ( name, lastname, email) VALUES ( ?, ?, ?);";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, client.getName());
            statement.setString(2, client.getLastname());
            statement.setString(3, client.getEmail());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            while (rs.next()) {
                client.setId(rs.getInt(1));
            }
            JOptionPane.showMessageDialog(null, "Client insertion completed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return client;
    }

    @Override
    public void update(Object object) {
        objConnection = ConfigurationDB.openConnection();
        Client client = (Client) object;
        try {
            String sql = "UPDATE client SET name = ?, lastname = ?, email = ? WHERE id = ?;";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setString(1, client.getName());
            statement.setString(2, client.getLastname());
            statement.setString(3, client.getEmail());
            statement.setInt(4, client.getId());
            statement.execute();

            if (statement.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Client updating completed successfully");
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
        Client airplane = (Client) object;
        try {
            String sql = "DELETE FROM client WHERE id =?";
            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, airplane.getId());
            statement.execute();

            if (statement.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Client deleting completed successfully");
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
        Client client = new Client();
        try {
            String sql = "SELECT * FROM client WHERE id = ?;";

            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setInt(1, (int) object);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setLastname(resultSet.getString("lastname"));
            client.setEmail(resultSet.getString("email"));

        } catch (Exception e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return client;
    }

    @Override
    public List<Object> findAll() {
        objConnection = ConfigurationDB.openConnection();
        List<Client> clients = new ArrayList<>();
        try {
            String sql = "SELECT * FROM client";
            try (PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    client.setId(resultSet.getInt("id"));
                    client.setName(resultSet.getString("name"));
                    client.setLastname(resultSet.getString("lastname"));
                    client.setEmail(resultSet.getString("email"));

                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
        ConfigurationDB.closeConnection();
        return Collections.singletonList(clients);
    }

    @Override
    public Object find(String string) {
        objConnection = ConfigurationDB.openConnection();
        Client client = new Client();
        try {
            String sql = "SELECT * FROM client WHERE name like ?;";

            PreparedStatement statement = (PreparedStatement) objConnection.prepareStatement(sql);
            statement.setString(1, "%" + string + "%");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setLastname(resultSet.getString("lastname"));
            client.setEmail(resultSet.getString("email"));

        } catch (Exception e) {
            ConfigurationDB.closeConnection();
            throw new RuntimeException(e);
        }
        ConfigurationDB.closeConnection();
        return client;
    }
}
