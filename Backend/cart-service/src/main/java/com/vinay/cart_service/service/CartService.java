package com.vinay.cart_service.service;


import com.vinay.cart_service.dto.CartDTO;
import com.vinay.cart_service.dto.CartItemDTO;
import com.vinay.cart_service.request.AddCartItemRequest;

public interface CartService {

    public CartItemDTO addItemToCart(AddCartItemRequest request) throws Exception;
    public CartItemDTO updateCartItemQuantity(Long cartItemId, Integer quantity) throws Exception;
    public CartDTO removeItemFromCart(Long cartItemId, Long userId) throws Exception;
    public Long calculateCartTotals(CartDTO cart);
    public CartDTO findCartById(Long id) throws Exception;
    public CartDTO findCartByUserId(Long userId);
    public CartDTO cleanCart(Long id) throws Exception;
}
