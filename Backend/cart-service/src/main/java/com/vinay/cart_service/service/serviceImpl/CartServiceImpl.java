package com.vinay.cart_service.service.serviceImpl;

import com.vinay.cart_service.dto.CartDTO;
import com.vinay.cart_service.dto.CartItemDTO;
import com.vinay.cart_service.dto.FoodDTO;
import com.vinay.cart_service.dto.UserDTO;
import com.vinay.cart_service.entity.Cart;
import com.vinay.cart_service.entity.CartItem;
import com.vinay.cart_service.repository.CartItemRepository;
import com.vinay.cart_service.repository.CartRepository;
import com.vinay.cart_service.request.AddCartItemRequest;
import com.vinay.cart_service.service.CartService;
import com.vinay.cart_service.service.external.FoodService;
import com.vinay.cart_service.service.external.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;
    private final ModelMapper modelMapper;

    @Override
    public CartItemDTO addItemToCart(AddCartItemRequest request) throws Exception {
        UserDTO userById = userService.getUserById(request.getUserId());
        FoodDTO food = foodService.getFoodById(request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(request.getUserId()).orElseThrow(() -> new RuntimeException("Cart not found"));


        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFoodId().equals(food.getId())) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }


        CartItem newItem = CartItem.builder()
                .quantity(request.getQuantity())
                .ingredients(request.getIngredients())
                .cart(cart)
                .totalPrice((request.getQuantity() * food.getPrice()))
                .build();


        return modelMapper.map(cartItemRepository.save(newItem), CartItemDTO.class);
    }

    @Override
    public CartItemDTO updateCartItemQuantity(Long cartItemId, Integer quantity) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item not found"));
        cartItem.setQuantity(quantity);
        CartItem cartSave = cartItemRepository.save(cartItem);
        CartItemDTO cartItemDTO = modelMapper.map(cartSave, CartItemDTO.class);
        FoodDTO food = foodService.getFoodById(cartItemDTO.getFoodId());
        cartItem.setTotalPrice((food.getPrice() * quantity));          // food-price = 200 , quantity = 4  => food-price * quantity = 800
        return cartItemDTO;
    }


    @Override
    public CartDTO removeItemFromCart(Long cartItemId, Long userId) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item not found"));
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().remove(cartItem);
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDTO.class);
    }

    @Override
    public Long calculateCartTotals(CartDTO cart) {
        Long total = 0L;
        for (CartItemDTO cartItem : cart.getItems()) {
            FoodDTO food = foodService.getFoodById(cartItem.getFoodId());
            total += food.getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public CartDTO findCartById(Long cardId) throws Exception {
        Cart cart = cartRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Cart not found"));
        return modelMapper.map(cart, CartDTO.class);
    }

    @Override
    public CartDTO findCartByUserId(Long id) {
        Cart cart = cartRepository.findByCustomerId(id).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.setTotal(calculateCartTotals(cartDTO));
        return cartDTO;
    }

    @Override
    public CartDTO cleanCart(Long id) throws Exception {
        CartDTO cart = findCartByUserId(id);
        cart.getItems().clear();
        return cart;
    }
}