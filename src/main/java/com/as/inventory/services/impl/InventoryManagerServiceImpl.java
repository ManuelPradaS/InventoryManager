package com.as.inventory.services.impl;

import com.as.inventory.model.ProductArray;
import com.as.inventory.model.Product;
import com.as.inventory.repository.ProductsRepository;
import com.as.inventory.services.api.InventoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class InventoryManagerServiceImpl implements InventoryManagerService {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    public InventoryManagerServiceImpl() {
    }


    @Override
    public ResponseEntity<String> addProduct(Product newProduct) {
        String responseMessage;
        if (newProduct.getName() == null || newProduct.getSupplier() == null || newProduct.getReference() == 0) {
            responseMessage = "All the fields must be filled";
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

        }
        try {
            productsRepository.save(newProduct);
            responseMessage = "Your product has been saved";
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch (NullPointerException e) {
            responseMessage = "An error has occurred";
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<ProductArray> getProducts(String name, Integer reference, String supplier, Integer productId) {

        List<Predicate<Product>> filters = new ArrayList<>();

        if (name != null) {
            filters.add(product -> product.getName().equals(name));
        }
        if (reference != null) {
            filters.add(product -> product.getReference() == reference);
        }
        if (supplier != null) {
            filters.add(product -> product.getSupplier().equals(supplier));
        }
        if (productId != null) {
            filters.add(product -> product.getProductId() == productId);
        }

        List<Product> allProducts = productsRepository.findAll();

        List<Product> searchResult = allProducts.stream()
                .filter(filters.stream().reduce(x -> true, Predicate::and)).collect(Collectors.toList());

        ProductArray results = new ProductArray(searchResult.toArray(new Product[searchResult.size()]));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer productId) {
        String responseMessage;

        try {
            productsRepository.deleteById(productId);
            responseMessage = "The product with id " + productId + " has been deleted";
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch (RuntimeException e) {
            responseMessage = "The product with id " + productId + " does not exist";
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

        }
    }
}
