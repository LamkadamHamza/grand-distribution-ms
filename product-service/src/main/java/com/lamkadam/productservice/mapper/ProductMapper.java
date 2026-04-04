package com.lamkadam.productservice.mapper;


import com.lamkadam.productservice.dto.ProductDTO;
import com.lamkadam.productservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductMapper {

     private  final ModelMapper modelMapper;


     public ProductDTO toDTO(Product product){
        return modelMapper.map(product,ProductDTO.class);
     }

     public Product toEntity(ProductDTO productDTO){
         return modelMapper.map(productDTO,Product.class);
     }
}
