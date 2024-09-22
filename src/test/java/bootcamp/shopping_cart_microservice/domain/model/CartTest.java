package bootcamp.shopping_cart_microservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    @DisplayName("Constructor initializes all fields correctly")
    @Test
    void constructorInitializesAllFieldsCorrectly() {
        Long id = 1L;
        Long userId = 2L;
        List<CartItem> items = Collections.singletonList(new CartItem());
        LocalDate createdAt = LocalDate.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        Cart cart = new Cart(id, userId, items, createdAt, updatedAt);

        assertEquals(id, cart.getId());
        assertEquals(userId, cart.getUserId());
        assertEquals(items, cart.getItems());
        assertEquals(createdAt, cart.getCreatedAt());
        assertEquals(updatedAt, cart.getUpdatedAt());
    }
    @DisplayName("Get cart ID successfully")
    @Test
    void setUserIdSuccessfully() {
        Cart cart = new Cart();
        cart.setUserId(1L);
        assertEquals(1L, cart.getUserId());
    }

    @DisplayName("Get cart ID successfully")
    @Test
    void setCartIdSuccessfully() {
        Cart cart = new Cart();
        cart.setId(1L);
        assertEquals(1L, cart.getId());
    }

    @DisplayName("Get items successfully")
    @Test
    void getItemsSuccessfully() {
        CartItem item = new CartItem();
        List<CartItem> items = Collections.singletonList(item);
        Cart cart = new Cart();
        cart.setItems(items);
        assertEquals(items, cart.getItems());
    }

    @DisplayName("Get created at date successfully")
    @Test
    void getCreatedAtDateSuccessfully() {
        LocalDate date = LocalDate.now();
        Cart cart = new Cart();
        cart.setCreatedAt(date);
        assertEquals(date, cart.getCreatedAt());
    }

    @DisplayName("Get updated at date successfully")
    @Test
    void getUpdatedAtDateSuccessfully() {
        LocalDateTime dateTime = LocalDateTime.now();
        Cart cart = new Cart();
        cart.setUpdatedAt(dateTime);
        assertEquals(dateTime, cart.getUpdatedAt());
    }
}