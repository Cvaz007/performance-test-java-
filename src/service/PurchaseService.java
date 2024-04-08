package service;


import entity.Product;
import entity.Purchase;
import model.ProductModel;
import model.PurchaseModel;

import javax.swing.*;

public class PurchaseService {
    ProductModel productModel;
    PurchaseModel purchaseModel;

    public PurchaseService() {
        this.productModel = new ProductModel();
        this.purchaseModel = new PurchaseModel();
    }

    public int currentStock(int quantity, int productId, int purchaseId) {
        Product product = (Product) productModel.find(productId);
        if (purchaseId != 0) {
            Purchase currentPurchase = (Purchase) purchaseModel.find(purchaseId);

            if (currentPurchase.getQuantity() > quantity) {
                return product.getStock() + (currentPurchase.getQuantity() - quantity);
            } else if (currentPurchase.getQuantity() < quantity) {
                return product.getStock() - (quantity - currentPurchase.getQuantity());
            }
            return product.getStock();
        }
        return product.getStock() - quantity;
    }

    public void updateStock(int stock, int productId) {
        System.out.println("stock:" + stock + " id:" + productId);
        productModel.updateStock(stock, productId);
    }

    public void showPurchase(Purchase purchaseSaved) {
        Purchase purchase = (Purchase) purchaseModel.find(purchaseSaved.getId());
        String purchaseText = "PURCHASE"
                + "\n PRODUCT."
                + "\n Name: " + purchase.getProduct().getName()
                + "\n Price: " + purchase.getProduct().getPrice()
                + "\n STORE."
                + "\n Name: " + purchase.getProduct().getStore().getName()
                + "\n Location: " + purchase.getProduct().getStore().getLocation()
                + "\n CLIENT."
                + "\n Name: " + purchase.getClient().getName()
                + "\n last name: " + purchase.getClient().getLastname()
                + "\n email: " + purchase.getClient().getEmail()
                + "\n ---------------------------------------"
                + "\n Total: " + purchase.getQuantity() * purchase.getProduct().getPrice()
                + "\n Total + IVA: " + (purchase.getQuantity() * purchase.getProduct().getPrice()) * 1.19;
        JOptionPane.showMessageDialog(null, purchaseText);
    }
}
