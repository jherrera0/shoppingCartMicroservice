package bootcamp.shopping_cart_microservice.infrastructure.controller;

import bootcamp.shopping_cart_microservice.application.http.dto.request.addArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController(JwtConst.CART_REST_CONTROLLER_RUTE)
@RequiredArgsConstructor
public class ShoppingCartRestController {

    private final ICartHandler cartHandler;

    @PostMapping(JwtConst.ADD_TO_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void addToCart(@RequestHeader(JwtConst.AUTHORIZATION) String token, @RequestBody addArticleRequest request) {
        cartHandler.addItem(token, request);
    }

    @DeleteMapping(JwtConst.DELETE_FROM_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void removeFromCart() {
        // Remove from cart logic
    }

    @PostMapping(JwtConst.BUY_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void buyCart() {
        // Get cart logic
    }
}
