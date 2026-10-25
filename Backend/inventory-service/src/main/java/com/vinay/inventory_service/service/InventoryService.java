package com.vinay.inventory_service.service;

import org.springframework.stereotype.Repository;

@Repository
public interface InventoryService {

    boolean isInStock(String skuCode, Integer quantity);

}
