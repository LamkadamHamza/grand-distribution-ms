package com.lamkadam.productservice.controller;


import com.lamkadam.productservice.dto.ProductDTO;
import com.lamkadam.productservice.entity.Product;
import com.lamkadam.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public ResponseEntity<List<ProductDTO>>   getAllProducts(){
        logger.info("GET /api/products - Fetching all products");
       List<ProductDTO> products = productService.getAllProducts();
       return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO>  getById(@PathVariable Long id) {
        logger.info("GET /api/products/{} - Fetching product by id", id);
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


    @PostMapping
    public ResponseEntity<ProductDTO>  create(@Valid @RequestBody ProductDTO product) {

        logger.info("POST /api/products - Creating product with name={}", product.getName());
        ProductDTO savedProduct = productService.saveProduct(product);

        return ResponseEntity.status(201).body(savedProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO product) {
        logger.info("PUT /api/products/{} - Updating product", id);
        ProductDTO updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("DELETE /api/products/{} - Deleting product", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
