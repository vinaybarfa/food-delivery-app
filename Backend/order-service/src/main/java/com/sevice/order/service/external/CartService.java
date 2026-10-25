package com.sevice.order.service.external;

import com.sevice.order.dto.CartDTO;
import com.sevice.order.dto.CartItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CART-SERVICE")
public interface CartService {

    @GetMapping("/api/cart/user/{userId}")
    CartDTO getUserCart(@PathVariable("userId") Long userId);

    @GetMapping("/api/cart/user/{userId}/total")
    Long getCartTotal(@PathVariable("userId") Long userId);

    @DeleteMapping("/api/cart/clear/{userId}")
    String clearCart(@PathVariable("userId") Long userId);

//    @PostMapping("/cart/add")
//    CartItemDTO addItemToCart(@RequestBody AddCartItemRequest request);
//
//    @PutMapping("/cart-item/update")
//    CartItemDTO updateCartItemQuantity(@RequestBody CartItemRequest cartItemRequest);

    @DeleteMapping("/cart-item/{cartItemId}/remove/{userId}")
    CartItemDTO removeItemFromCart(@PathVariable Long cartItemId, @PathVariable Long userId);

    @PostMapping("/cart/calculate")
    Long calculateCartTotals(@RequestBody CartDTO cart);

    @GetMapping("/cart/{cartId}")
    CartItemDTO findCartById(@PathVariable Long cardId);

    @GetMapping("/user-cart/{userId}")
    CartItemDTO findUserCart(@PathVariable Long userId);

    @PutMapping("/clear/{userId}")
    CartItemDTO cleanCart(@PathVariable Long userId);
}
