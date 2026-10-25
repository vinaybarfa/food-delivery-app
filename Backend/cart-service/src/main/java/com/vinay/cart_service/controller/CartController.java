package com.vinay.cart_service.controller;

import com.vinay.cart_service.dto.CartDTO;
import com.vinay.cart_service.dto.CartItemDTO;
import com.vinay.cart_service.request.AddCartItemRequest;
import com.vinay.cart_service.request.CartItemRequest;
import com.vinay.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
//    private final UserService userService;

    @PostMapping("/cart/add")
    public ResponseEntity<CartItemDTO> addItemToCart(@RequestBody AddCartItemRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addItemToCart(request));
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItemDTO> updateCartItemQuantity(@RequestBody CartItemRequest cartItemRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.updateCartItemQuantity(cartItemRequest.getCartItemId(), cartItemRequest.getQuantity()));

    }

    @DeleteMapping("/cart-item/{cartItemId}/remove/{userId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long cartItemId,@PathVariable Long userId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.removeItemFromCart(cartItemId, userId));

    }

    @PostMapping("/cart/calculate")
    public ResponseEntity<Long> calculateCartTotals(@RequestBody CartDTO cart) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.calculateCartTotals(cart));
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<CartDTO> findCartById(@PathVariable Long cardId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartById(cardId));
    }

    @GetMapping("/user-cart/{userId}")
    public ResponseEntity<CartDTO> findUserCart(@PathVariable Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.findCartByUserId(userId));
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<CartDTO> cleanCart(@PathVariable Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.cleanCart(userId));
    }

}
