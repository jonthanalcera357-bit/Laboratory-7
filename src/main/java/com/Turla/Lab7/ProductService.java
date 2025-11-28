package com.Turla.Lab7;

import com.Turla.Lab7.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> productDatabase = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(3);

    public ProductService() {
        productDatabase.put(1L, new Product(1L, "Laptop Pro", 1299.99));
        productDatabase.put(2L, new Product(2L, "Wireless Mouse", 29.99));
        productDatabase.put(3L, new Product(3L, "Mechanical Keyboard", 89.99));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productDatabase.values());
    }

    public Product getProductById(Long id) {
        return productDatabase.get(id);
    }

    public Product createProduct(Product product) {
        Long newId = idCounter.incrementAndGet();
        product.setId(newId);
        productDatabase.put(newId, product);
        return product;
    }

    public Product updateProduct(Long id, Product product) {
        if (productDatabase.containsKey(id)) {
            product.setId(id);
            productDatabase.put(id, product);
            return product;
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        return productDatabase.remove(id) != null;
    }

    public boolean productExists(Long id) {
        return productDatabase.containsKey(id);
    }
}