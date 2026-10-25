package com.vinay.inventory_service.service;

import com.vinay.inventory_service.dto.ProductDTO;
import com.vinay.inventory_service.entity.Product;
import com.vinay.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Product addProduct(ProductDTO product) {


        ProductDTO newProduct = product.builder()
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();

        return productRepository.save(modelMapper.map(newProduct, Product.class));
    }

    public List<Product> viewAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProductDetail(Map<String, Object> update, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));

        update.forEach((field, value) -> {
            switch (field) {
                case "productName":
                    product.setProductName((String) value);
                    break;
                case "quantity":
                    product.setQuantity((Integer) value);
                case "price":
                    product.setPrice((Double) value);
                default:
                    new IllegalArgumentException("Can't update");
                    break;
            }
        });
        Product updated = modelMapper.map(update, Product.class);
        return productRepository.save(updated);
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

}

