package bootcamp.shopping_cart_microservice.domain.usecase;

import bootcamp.shopping_cart_microservice.domain.exception.CategoriesLimitExceededException;
import bootcamp.shopping_cart_microservice.domain.exception.StockNotEnoughException;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import bootcamp.shopping_cart_microservice.domain.spi.*;
import bootcamp.shopping_cart_microservice.domain.until.ExceptionConst;
import bootcamp.shopping_cart_microservice.domain.until.TokenHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CartCaseTest {

    @Mock
    private ICartPersistencePort cartPersistencePort;

    @Mock
    private IJwtPersistencePort jwtPersistencePort;

    @Mock
    private ICartItemPersistencePort cartItemPersistencePort;

    @Mock
    private IFeignStockPersistencePort feignStockPersistencePort;

    @Mock
    private IFeignSupplyPersistencePort feignSupplyPersistencePort;

    @Mock
    private CartCase cartCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TokenHolder.setToken("validToken");
        cartCase = new CartCase(cartPersistencePort, jwtPersistencePort, feignStockPersistencePort, feignSupplyPersistencePort, cartItemPersistencePort);
    }
    @DisplayName("Add item to cart successfully")
    @Test
    void addItemToCartSuccessfully() {
        Long productId = 1L;
        Long quantity = 2L;
        CartItem itemOnStock = new CartItem();
        itemOnStock.setQuantity(10L);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(Collections.emptyList());

        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(any())).thenReturn(1L);
        when(cartPersistencePort.getCartByUserId(any())).thenReturn(cart);
        when(cartItemPersistencePort.getCartItemsOnCart(any())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> cartCase.addItem(productId, quantity));
        verify(cartItemPersistencePort).addCartItem(any(CartItem.class));
        verify(cartPersistencePort).updateCart(any(Cart.class));
    }


    @DisplayName("Throw exception when stock is not enough")
    @Test
    void throwExceptionWhenStockIsNotEnough() {
        Long productId = 1L;
        Long quantity = 20L;
        CartItem itemOnStock = new CartItem();
        itemOnStock.setQuantity(10L);
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(Collections.emptyList());

        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(any())).thenReturn(1L);
        when(cartPersistencePort.getCartByUserId(any())).thenReturn(cart);
        when(cartItemPersistencePort.getCartItemsOnCart(any())).thenReturn(Collections.emptyList());
        when(feignSupplyPersistencePort.getNextSupplyDate(productId)).thenReturn("2023-12-01");

        StockNotEnoughException exception = assertThrows(StockNotEnoughException.class, () -> cartCase.addItem(productId, quantity));
        assertTrue(exception.getMessage().contains(ExceptionConst.STOCK_NOT_ENOUGH));
    }
    @Test
    void addItem_shouldIncreaseQuantity_whenItemAlreadyInCart() {
        Long productId = 1L;
        Long quantity = 2L;
        Long userId = 123L;
        Cart cart = new Cart();
        cart.setId(1L);

        CartItem existingItem = new CartItem();
        existingItem.setProductId(productId);
        existingItem.setQuantity(3L);

        List<CartItem> items = new ArrayList<>();
        items.add(existingItem);
        cart.setItems(items);

        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(existingItem);
        when(jwtPersistencePort.getUserId(anyString())).thenReturn(userId);
        when(cartPersistencePort.getCartByUserId(userId)).thenReturn(cart);
        when(cartItemPersistencePort.getCartItemsOnCart(cart.getId())).thenReturn(items);
        cartCase.addItem(productId, quantity);
        assertEquals(5L, existingItem.getQuantity());
        verify(cartItemPersistencePort, times(1)).updateCartItem(existingItem);
        verify(cartPersistencePort, times(1)).updateCart(any(Cart.class));
    }
    @Test
    void addItem_shouldThrowException_whenStockIsNotEnough() {
        Long productId = 1L;
        Long quantity = 10L;
        Long userId = 123L;
        Cart cart = new Cart();
        cart.setId(1L);
        CartItem itemOnStock = new CartItem();
        itemOnStock.setProductId(productId);
        itemOnStock.setQuantity(5L);
        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(anyString())).thenReturn(userId);
        when(cartPersistencePort.getCartByUserId(userId)).thenReturn(cart);
        StockNotEnoughException exception = assertThrows(StockNotEnoughException.class, () -> {cartCase.addItem(productId, quantity); });
        assertTrue(exception.getMessage().contains(ExceptionConst.STOCK_NOT_ENOUGH));
    }
    @Test
    void shouldAddNewItemToCart_whenItemDoesNotExistInCart() {
        Long productId = 4L;
        Long quantity = 1L;
        Long userId = 123L;
        Cart cart = new Cart();
        cart.setId(1L);
        CartItem existingItem = new CartItem(1L, 3L, 3L, "product", "brand", 1.1, List.of("Books"), cart);
        CartItem existingItem2 = new CartItem(2L, 2L, 3L, "product2", "brand", 1.1, List.of("Books"), cart);
        CartItem existingItem3 = new CartItem(3L, 1L, 3L, "product3", "brand", 1.1, List.of("Books"), cart);
        existingItem.setCategories(new ArrayList<>(List.of("Books")));
        existingItem2.setCategories(new ArrayList<>(List.of("Books")));
        existingItem3.setCategories(new ArrayList<>(List.of("Books")));
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(existingItem);
        cartItems.add(existingItem2);
        cartItems.add(existingItem3);
        cart.setItems(cartItems);
        CartItem itemOnStock = new CartItem();
        itemOnStock.setProductId(productId);
        itemOnStock.setQuantity(10L);
        itemOnStock.setCategories(new ArrayList<>(List.of("test")));
        CartItem newItem = new CartItem();
        newItem.setProductId(productId);
        newItem.setQuantity(quantity);
        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(anyString())).thenReturn(userId);
        when(cartPersistencePort.getCartByUserId(userId)).thenReturn(cart);
        when(cartItemPersistencePort.getCartItemsOnCart(cart.getId())).thenReturn(cartItems);
        cartCase.addItem(productId, quantity);
        verify(cartItemPersistencePort, times(1)).addCartItem(any(CartItem.class));
        verify(cartItemPersistencePort, never()).updateCartItem(any(CartItem.class));

        verify(cartPersistencePort, times(1)).updateCart(cart);

        assertNotNull(cart.getUpdatedAt());
        LocalDateTime beforeNow = LocalDateTime.now();
        assertTrue(cart.getUpdatedAt().isBefore(beforeNow.plusSeconds(1)));
    }
    @Test
    void shouldThrowException_whenMoreThanThreeItemsOfSameCategory() {
        Long productId = 4L;
        Long quantity = 1L;
        Long userId = 123L;
        Cart cart = new Cart();
        cart.setId(1L);
        CartItem existingItem1 = new CartItem(1L, 3L, 3L, "product1", "brand", 1.1, List.of("Books"), cart);
        CartItem existingItem2 = new CartItem(2L, 2L, 3L, "product2", "brand", 1.1, List.of("Books"), cart);
        CartItem existingItem3 = new CartItem(3L, 1L, 3L, "product3", "brand", 1.1, List.of("Books"), cart);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(existingItem1);
        cartItems.add(existingItem2);
        cartItems.add(existingItem3);
        cart.setItems(cartItems);
        CartItem itemOnStock = new CartItem();
        itemOnStock.setProductId(productId);
        itemOnStock.setQuantity(10L);
        itemOnStock.setCategories(List.of("Books"));

        CartItem newItem = new CartItem();
        newItem.setProductId(productId);
        newItem.setQuantity(quantity);
        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(anyString())).thenReturn(userId);
        when(cartPersistencePort.getCartByUserId(userId)).thenReturn(cart);
        when(cartItemPersistencePort.getCartItemsOnCart(cart.getId())).thenReturn(cartItems);
        assertThrows(CategoriesLimitExceededException.class, () -> {
            cartCase.addItem(productId, quantity);
        });
        verify(cartItemPersistencePort, never()).addCartItem(any(CartItem.class));
        verify(cartPersistencePort, never()).updateCart(cart);
    }

    @DisplayName("Create cart if not exists when adding item")
    @Test
    void createCartIfNotExistsWhenAddingItem() {
        Long productId = 1L;
        Long quantity = 2L;
        CartItem itemOnStock = new CartItem();
        itemOnStock.setQuantity(10L);

        when(feignStockPersistencePort.findCartItemById(productId)).thenReturn(itemOnStock);
        when(jwtPersistencePort.getUserId(any())).thenReturn(1L);
        when(cartPersistencePort.getCartByUserId(any()))
                .thenReturn(null)
                .thenReturn(new Cart());
        when(cartItemPersistencePort.getCartItemsOnCart(any())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> cartCase.addItem(productId, quantity));
        verify(cartPersistencePort).createCart(any(Long.class));
    }

}