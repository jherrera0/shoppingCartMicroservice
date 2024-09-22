package bootcamp.shopping_cart_microservice.application.jpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CartItemEntityTest {

    @DisplayName("Create CartItemEntity with valid data")
    @Test
    void createCartItemEntityWithValidData() {
        CartEntity cart = new CartEntity();
        Long productId = 1L;
        Long quantity = 2L;
        String productName = "Product";
        String brandName = "Brand";
        Double price = 10.0;
        List<String> categories = Arrays.asList("Category1", "Category2");

        CartItemEntity cartItemEntity = new CartItemEntity(cart, productId, quantity, productName, brandName, price, categories);

        assertEquals(cart, cartItemEntity.getCart());
        assertEquals(productId, cartItemEntity.getProductId());
        assertEquals(quantity, cartItemEntity.getQuantity());
        assertEquals(productName, cartItemEntity.getProductName());
        assertEquals(brandName, cartItemEntity.getBrandName());
        assertEquals(price, cartItemEntity.getPrice());
        assertEquals(categories, cartItemEntity.getCategories());
    }

    @DisplayName("Create CartItemEntity with null values")
    @Test
    void createCartItemEntityWithNullValues() {
        CartItemEntity cartItemEntity = new CartItemEntity(null, null, null, null, null, null, null);

        assertNull(cartItemEntity.getCart());
        assertNull(cartItemEntity.getProductId());
        assertNull(cartItemEntity.getQuantity());
        assertNull(cartItemEntity.getProductName());
        assertNull(cartItemEntity.getBrandName());
        assertNull(cartItemEntity.getPrice());
        assertNull(cartItemEntity.getCategories());
    }

    @DisplayName("Set and get CartItemEntity fields")
    @Test
    void setAndGetCartItemEntityFields() {
        CartItemEntity cartItemEntity = new CartItemEntity();
        CartEntity cart = new CartEntity();
        Long productId = 1L;
        Long quantity = 2L;
        String productName = "Product";
        String brandName = "Brand";
        Double price = 10.0;
        List<String> categories = Arrays.asList("Category1", "Category2");

        cartItemEntity.setCart(cart);
        cartItemEntity.setProductId(productId);
        cartItemEntity.setQuantity(quantity);
        cartItemEntity.setProductName(productName);
        cartItemEntity.setBrandName(brandName);
        cartItemEntity.setPrice(price);
        cartItemEntity.setCategories(categories);

        assertEquals(cart, cartItemEntity.getCart());
        assertEquals(productId, cartItemEntity.getProductId());
        assertEquals(quantity, cartItemEntity.getQuantity());
        assertEquals(productName, cartItemEntity.getProductName());
        assertEquals(brandName, cartItemEntity.getBrandName());
        assertEquals(price, cartItemEntity.getPrice());
        assertEquals(categories, cartItemEntity.getCategories());
    }
}