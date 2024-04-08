package controller;

import entity.Product;
import model.ProductModel;
import model.PurchaseModel;
import repository.CrudRepository;
import repository.GetByStringRepository;
import utils.AttributeInfo;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class GenericController extends Utils {

    public void getAll(CrudRepository model) {
        List<Object> list = model.findAll();
        String listString = "LIST OF REGISTERS \n";
        for (Object item : list) {
            listString += item.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, listString);
    }

    public void get(CrudRepository model) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("\n¿which do you want to find?"));
        JOptionPane.showMessageDialog(null, model.find(id).toString());
    }

    public void getString(GetByStringRepository model){
        String string = JOptionPane.showInputDialog("Type product name or store name");
        JOptionPane.showMessageDialog(null, model.find(string).toString());
    }

    public void getAllByProduct(PurchaseModel purchaseModel) {
        int option = Integer.parseInt(JOptionPane.showInputDialog(null, "Type product id"));
        String listString = "LIST OF REGISTERS \n";
        for (Object item : purchaseModel.findAllBySomething(option)) {
            listString += item.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, listString);
    }

    public void delete(CrudRepository model) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("\n¿which do you want to delete?"));
        model.delete(model.find(id));
    }

    public void save(CrudRepository model, Class<?> entity) {
        List<AttributeInfo> list = extractAttributesInfo(entity);

        model.save(createObjectDynamically(entity, list));
    }

    public void update(CrudRepository model, Class<?> entity) {
        List<AttributeInfo> list = extractAttributesInfo(entity);
        model.update(createObjectDynamically(entity, list));
    }
}
