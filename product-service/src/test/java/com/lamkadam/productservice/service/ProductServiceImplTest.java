package com.lamkadam.productservice.service;


import com.lamkadam.productservice.dto.ProductDTO;
import com.lamkadam.productservice.entity.Product;
import com.lamkadam.productservice.exception.ResourceNotFoundException;
import com.lamkadam.productservice.mapper.ProductMapper;
import com.lamkadam.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;



    @Test
    void shouldReturnAllProducts() {
        Product product1 = Product.builder()
                .id(1L)
                .name("PC")
                .price(1000.0)
                .quantity(5)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("Phone")
                .price(500.0)
                .quantity(10)
                .build();

        ProductDTO dto1 = ProductDTO.builder()
                .name("PC")
                .price(1000.0)
                .quantity(5)
                .build();

        ProductDTO dto2 = ProductDTO.builder()
                .name("Phone")
                .price(500.0)
                .quantity(10)
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toDTO(product1)).thenReturn(dto1);
        when(productMapper.toDTO(product2)).thenReturn(dto2);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("PC", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDTO(product1);
        verify(productMapper, times(1)).toDTO(product2);
    }


    @Test
    void shouldReturnProductById() {
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("Laptop")
                .price(1200.0)
                .quantity(3)
                .build();

        ProductDTO dto = ProductDTO.builder()
                .name("Laptop")
                .price(1200.0)
                .quantity(3)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(dto);

        ProductDTO result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1200.0, result.getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toDTO(product);
    }


    @Test
    void shouldThrowExceptionWhenProductNotFoundById() {
        Long productId = 100L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals("Product not found with id : 100", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, never()).toDTO(any());
    }



    @Test
    void shouldSaveProduct() {
        ProductDTO inputDto = ProductDTO.builder()
                .name("Tablet")
                .price(300.0)
                .quantity(7)
                .build();

        Product productToSave = Product.builder()
                .name("Tablet")
                .price(300.0)
                .quantity(7)
                .build();

        Product savedProduct = Product.builder()
                .id(1L)
                .name("Tablet")
                .price(300.0)
                .quantity(7)
                .build();

        ProductDTO savedDto = ProductDTO.builder()
                .name("Tablet")
                .price(300.0)
                .quantity(7)
                .build();

        when(productMapper.toEntity(inputDto)).thenReturn(productToSave);
        when(productRepository.save(productToSave)).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(savedDto);

        ProductDTO result = productService.saveProduct(inputDto);

        assertNotNull(result);
        assertEquals("Tablet", result.getName());
        verify(productMapper, times(1)).toEntity(inputDto);
        verify(productRepository, times(1)).save(productToSave);
        verify(productMapper, times(1)).toDTO(savedProduct);
    }

    @Test
    void shouldUpdateProduct() {
        Long productId = 1L;

        ProductDTO updateDto = ProductDTO.builder()
                .name("Updated Product")
                .price(999.0)
                .quantity(20)
                .build();

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Old Product")
                .price(100.0)
                .quantity(2)
                .build();

        Product updatedProduct = Product.builder()
                .id(productId)
                .name("Updated Product")
                .price(999.0)
                .quantity(20)
                .build();

        ProductDTO updatedDto = ProductDTO.builder()
                .name("Updated Product")
                .price(999.0)
                .quantity(20)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
        when(productMapper.toDTO(updatedProduct)).thenReturn(updatedDto);

        ProductDTO result = productService.updateProduct(productId, updateDto);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(999.0, result.getPrice());
        assertEquals(20, result.getQuantity());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).toDTO(updatedProduct);
    }


    @Test
    void shouldDeleteProduct() {
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("Mouse")
                .price(50.0)
                .quantity(15)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
    }


    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        Long productId = 99L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProduct(productId)
        );

        assertEquals("Product not found with id: 99", exception.getMessage());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any());
    }
}
