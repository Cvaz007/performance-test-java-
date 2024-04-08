import connection.ConfigurationDB;
import controller.GenericController;
import entity.Client;
import entity.Product;
import entity.Purchase;
import model.ClientModel;
import model.ProductModel;
import model.PurchaseModel;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        GenericController controller = new GenericController();
        ClientModel clientModel = new ClientModel();
        ProductModel productModel = new ProductModel();
        PurchaseModel purchaseModel = new PurchaseModel();
        
        String optionEntity, optionCrud;
        String[] optionsEntity = {"1.CRUD CLIENT", "2.CRUD PRODUCT", "3.CRUD PURCHASE", "4. EXIT"};
        String[] optionsCrud = {"1. Create", "2. Delete", "3. Update", "4. List", "5. List all", "6. List by something", "7. Exit"};
        do {
            optionEntity = (String) JOptionPane.showInputDialog(
                    null,
                    "Select one option",
                    "Select Menu",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionsEntity,
                    optionsEntity[0]
            );
            System.out.println(optionEntity);
            switch (optionEntity) {
                case "1.CRUD CLIENT":
                    do {
                        optionCrud = (String) JOptionPane.showInputDialog(
                                null,
                                "Select one option",
                                "Select Menu",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                optionsCrud,
                                optionsCrud[0]);

                        switch (optionCrud) {
                            case "1. Create":
                                controller.save(clientModel, Client.class);
                                break;
                            case "2. Delete":
                                controller.delete(clientModel);
                                break;
                            case "3. Update":
                                controller.update(clientModel, Client.class);
                                break;
                            case "4. List":
                                controller.get(clientModel);
                                break;
                            case "5. List all":
                                controller.getAll(clientModel);
                                break;
                            case "7. Exit":
                                System.out.println("BYE!");
                                break;
                            default:
                                JOptionPane.showInputDialog("I'm sorry, this options is not valid with this entity");
                                break;
                        }
                    } while (!optionCrud.equalsIgnoreCase("7. Exit"));
                    break;
                case "2.CRUD PRODUCT":
                    do {
                        optionCrud = (String) JOptionPane.showInputDialog(
                                null,
                                "Select one option",
                                "Select Menu",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                optionsCrud,
                                optionsCrud[0]);

                        switch (optionCrud) {
                            case "1. Create":
                                controller.save(productModel, Product.class);
                                break;
                            case "2. Delete":
                                controller.delete(productModel);
                                break;
                            case "3. Update":
                                controller.update(productModel, Product.class);
                                break;
                            case "4. List":
                                controller.get(productModel);
                                break;
                            case "5. List all":
                                controller.getAll(productModel);
                                break;
                            case "7. Exit":
                                System.out.println("BYE!");
                                break;
                            default:
                                JOptionPane.showInputDialog("I'm sorry, this options is not valid with this entity");
                                break;
                        }
                    } while (!optionCrud.equalsIgnoreCase("7. Exit"));
                    break;
                case "3.CRUD PURCHASE":
                    do {
                        optionCrud = (String) JOptionPane.showInputDialog(
                                null,
                                "Select one option",
                                "Select Menu",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                optionsCrud,
                                optionsCrud[0]);

                        switch (optionCrud) {
                            case "1. Create":
                                controller.save(purchaseModel, Purchase.class);
                                break;
                            case "2. Delete":
                                controller.delete(purchaseModel);
                                break;
                            case "3. Update":
                                controller.update(purchaseModel, Purchase.class);
                                break;
                            case "4. List":
                                controller.get(purchaseModel);
                                break;
                            case "5. List all":
                                controller.getAll(purchaseModel);
                                break;
                            case "7. Exit":
                                System.out.println("BYE!");
                                break;
                            default:
                                JOptionPane.showInputDialog("I'm sorry, this options is not valid with this entity");
                                break;
                        }
                    } while (!optionCrud.equalsIgnoreCase("7. Exit"));
                    break;
            }
        } while (!optionEntity.equalsIgnoreCase("4. EXIT"));
    }
}