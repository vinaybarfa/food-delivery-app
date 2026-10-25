package com.vinay.inventory_service.controller;

import com.vinay.inventory_service.dto.ProductDTO;
import com.vinay.inventory_service.entity.Product;
import com.vinay.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.addProduct(product));
    }

    @GetMapping("/viewAllProducts")
    public ResponseEntity<List<Product>> viewAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.viewAllProducts());
    }
    @PatchMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProductDetail(@PathVariable Map<String, Object> update, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductDetail(update,id));
    }
    @DeleteMapping("/removeProduct/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
       return ResponseEntity.notFound().build();
    }

}
