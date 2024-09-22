package bootcamp.shopping_cart_microservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    @DisplayName("Constructor initializes all fields correctly")
    @Test
    void constructorInitializesAllFieldsCorrectly() {
        Long id = 1L;
        Long productId = 2L;
        Long quantity = 3L;
        String productName = "Product";
        String brandName = "Brand";
        Double price = 10.0;
        List<String> categories = Arrays.asList("Category1", "Category2");
        Cart cart = new Cart();

        CartItem cartItem = new CartItem(id, productId, quantity, productName, brandName, price, categories, cart);

        assertEquals(id, cartItem.getId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(productName, cartItem.getProductName());
        assertEquals(brandName, cartItem.getBrandName());
        assertEquals(price, cartItem.getPrice());
        assertEquals(categories, cartItem.getCategories());
        assertEquals(cart, cartItem.getCart());
    }

    @DisplayName("Copy constructor initializes all fields correctly")
    @Test
    void copyConstructorInitializesAllFieldsCorrectly() {
        Long id = 1L;
        Long productId = 2L;
        Long quantity = 3L;
        String productName = "Product";
        String brandName = "Brand";
        Double price = 10.0;
        List<String> categories = Arrays.asList("Category1", "Category2");
        Cart cart = new Cart();

        CartItem original = new CartItem(id, productId, quantity, productName, brandName, price, categories, cart);
        CartItem copy = new CartItem(original);

        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getProductId(), copy.getProductId());
        assertEquals(original.getQuantity(), copy.getQuantity());
        assertEquals(original.getProductName(), copy.getProductName());
        assertEquals(original.getBrandName(), copy.getBrandName());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getCategories(), copy.getCategories());
        assertEquals(original.getCart(), copy.getCart());
    }

    @DisplayName("Default constructor initializes fields to null or empty")
    @Test
    void defaultConstructorInitializesFieldsToNullOrEmpty() {
        CartItem cartItem = new CartItem();

        assertNull(cartItem.getId());
        assertNull(cartItem.getProductId());
        assertNull(cartItem.getQuantity());
        assertNull(cartItem.getProductName());
        assertNull(cartItem.getBrandName());
        assertNull(cartItem.getPrice());
        assertNull(cartItem.getCategories());
        assertNull(cartItem.getCart());
    }

    @DisplayName("Set and get null categories")
    @Test
    void setAndGetNullCategories() {
        CartItem cartItem = new CartItem();
        cartItem.setCategories(null);
        assertNull(cartItem.getCategories());
    }

    @DisplayName("Set and get null cart")
    @Test
    void setAndGetNullCart() {
        CartItem cartItem = new CartItem();
        cartItem.setCart(null);
        assertNull(cartItem.getCart());
    }
    @DisplayName("Set and get ID")
    @Test
    void setAndGetId() {
        CartItem cartItem = new CartItem();
        Long id = 1L;
        cartItem.setId(id);
        assertEquals(id, cartItem.getId());
    }

    @DisplayName("Set and get product ID")
    @Test
    void setAndGetProductId() {
        CartItem cartItem = new CartItem();
        Long productId = 2L;
        cartItem.setProductId(productId);
        assertEquals(productId, cartItem.getProductId());
    }

    @DisplayName("Set and get quantity")
    @Test
    void setAndGetQuantity() {
        CartItem cartItem = new CartItem();
        Long quantity = 3L;
        cartItem.setQuantity(quantity);
        assertEquals(quantity, cartItem.getQuantity());
    }

    @DisplayName("Set and get product name")
    @Test
    void setAndGetProductName() {
        CartItem cartItem = new CartItem();
        String productName = "Product";
        cartItem.setProductName(productName);
        assertEquals(productName, cartItem.getProductName());
    }

    @DisplayName("Set and get brand name")
    @Test
    void setAndGetBrandName() {
        CartItem cartItem = new CartItem();
        String brandName = "Brand";
        cartItem.setBrandName(brandName);
        assertEquals(brandName, cartItem.getBrandName());
    }

    @DisplayName("Set and get price")
    @Test
    void setAndGetPrice() {
        CartItem cartItem = new CartItem();
        Double price = 10.0;
        cartItem.setPrice(price);
        assertEquals(price, cartItem.getPrice());
    }

    @DisplayName("Set and get categories")
    @Test
    void setAndGetCategories() {
        CartItem cartItem = new CartItem();
        List<String> categories = Arrays.asList("Category1", "Category2");
        cartItem.setCategories(categories);
        assertEquals(categories, cartItem.getCategories());
    }

    @DisplayName("Set and get cart")
    @Test
    void setAndGetCart() {
        CartItem cartItem = new CartItem();
        Cart cart = new Cart();
        cartItem.setCart(cart);
        assertEquals(cart, cartItem.getCart());
    }
}