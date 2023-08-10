package com.example.demo.product;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryRepository;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/products")
    Iterable<Product> getProducts() {
        Iterable<Product> products = productRepository.findAll();
        return products;
    }

    @PostMapping("/product")
    ResponseEntity<?> postProduct(@RequestBody() ProductCreationBody body) {
        Optional<Category> category = categoryRepository.findById(body.categoryId());
        if(category.isPresent()){
            Product product = new Product(body.name(), body.description(), body.price(), category.get());
            Product p = productRepository.save(product);
            return ResponseEntity.ok(p);
        }else{
            return ResponseEntity.badRequest().body("NAO TEM categoria com esse id");
        }
    }
    public record ProductCreationBody(String name, String description, float price, Long categoryId) {
        
    }

    @PutMapping("/product/{id}")
    ResponseEntity<?> putProduct(@PathVariable Long id, @RequestBody Product body) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(body.getName());
            product.setDescription(body.getDescription());
            product.setPrice(body.getPrice());

            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body("Esse id não existe");
        }
    }

    @DeleteMapping("/product/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            productRepository.delete(product);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("Esse id não existe");
        }
    }
}
