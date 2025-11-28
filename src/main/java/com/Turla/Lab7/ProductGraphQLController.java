package com.Turla.Lab7;

import com.Turla.Lab7.Product;
import com.Turla.Lab7.ProductGraphQLController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductGraphQLController {

    private final ProductService productService;

    @Autowired
    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // Query - Get all products
    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Query - Get product by ID
    @QueryMapping
    public Product getProductById(@Argument Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        return product;
    }

    // Mutation - Create new product
    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        Product product = new Product();
        product.setName(input.name());
        product.setPrice(input.price());
        return productService.createProduct(product);
    }

    // Mutation - Update existing product
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput input) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Product not found with ID: " + id);
        }

        Product updatedProduct = new Product();
        updatedProduct.setName(input.name());
        updatedProduct.setPrice(input.price());

        return productService.updateProduct(id, updatedProduct);
    }

    // Mutation - Delete product
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    // Record for GraphQL input
    public record ProductInput(String name, Double price) {}
}