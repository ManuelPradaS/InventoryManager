package com.as.inventory.model;

public class ProductArray {
    private Product[] products;

    public ProductArray(Product[] products) {
        this.products = products;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
