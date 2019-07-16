package com.as.inventory.repository;

import com.as.inventory.model.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductsRepository extends CrudRepository<Product,Integer>  {
    @Override
    List<Product> findAll();
}
