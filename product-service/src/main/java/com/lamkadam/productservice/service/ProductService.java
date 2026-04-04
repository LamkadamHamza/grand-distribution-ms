package com.lamkadam.productservice.service;

import com.lamkadam.productservice.ProductServiceApplication;
import com.lamkadam.productservice.dto.ProductDTO;
import com.lamkadam.productservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id , ProductDTO productDTO);
    void deleteProduct(Long id);

}
