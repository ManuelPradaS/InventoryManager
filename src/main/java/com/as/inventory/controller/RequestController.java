package com.as.inventory.controller;

import com.as.inventory.model.ProductArray;
import com.as.inventory.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.as.inventory.services.api.InventoryManagerService;


@RestController
public class RequestController {

    private InventoryManagerService inventoryManagerService;

    @Autowired
    public RequestController(InventoryManagerService inventoryManagerService) {
        this.inventoryManagerService = inventoryManagerService;
    }

    @RequestMapping(path = "/products", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> saveProduct(@RequestBody Product newProduct) {
        return inventoryManagerService.addProduct(newProduct);

    }

    @RequestMapping(path = "/products", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<ProductArray> getProducts(@RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "reference", required = false) Integer reference,
                                                    @RequestParam(value = "supplier", required = false) String supplier,
                                                    @RequestParam(value = "id", required = false) Integer productId) {
        return inventoryManagerService.getProducts(name, reference, supplier, productId);
    }


}
