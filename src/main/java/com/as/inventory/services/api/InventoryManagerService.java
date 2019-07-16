package com.as.inventory.services.api;

import com.as.inventory.model.ProductArray;
import com.as.inventory.model.Product;
import org.springframework.http.ResponseEntity;

public interface InventoryManagerService {
    ResponseEntity<String> addProduct(Product newProduct);
    ResponseEntity<ProductArray> getProducts(String name,Integer reference,String supplier,Integer productId);
}
