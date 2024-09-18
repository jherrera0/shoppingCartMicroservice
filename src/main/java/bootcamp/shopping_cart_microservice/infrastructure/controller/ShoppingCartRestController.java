package bootcamp.shopping_cart_microservice.infrastructure.controller;

import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(JwtConst.CART_REST_CONTROLLER_RUTE)
public class ShoppingCartRestController {

    @PostMapping(JwtConst.ADD_TO_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void addToCart() {
        // Add to cart logic
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
