package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartEntity;
import bootcamp.shopping_cart_microservice.application.jpa.entity.CartItemEntity;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartItemEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartItemRepository;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartItemJpaAdapterTest {
    @Mock
    private ICartItemRepository cartItemJpaRepository;
    @Mock
    private ICartItemEntityMapper cartItemEntityMapper;
    @Mock
    private CartItemJpaAdapter cartItemJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItemJpaAdapter = new CartItemJpaAdapter(cartItemJpaRepository, cartItemEntityMapper);
    }
    @DisplayName("Update cart item with valid data")
    @Test
    void updateCartItemWithValidData() {
        CartItem cartItem = new CartItem(1L, 2L, 3L, "Product", "Brand", 10.0, Arrays.asList("Category1", "Category2"));
        cartItem.setCart(new Cart());
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setCart(new CartEntity());
        cartItemEntity.getCart().setId(1L);
        cartItemEntity.setProductId(2L);
        cartItemEntity.setQuantity(3L);
        when(cartItemEntityMapper.toEntity(cartItem)).thenReturn(cartItemEntity);

        cartItemJpaAdapter.updateCartItem(cartItem);

        verify(cartItemJpaRepository).updateCartItems(cartItemEntity.getQuantity(), cartItemEntity.getProductId(), cartItemEntity.getCart().getId());
    }

    @DisplayName("Add cart item with valid data")
    @Test
    void addCartItemWithValidData() {
        CartItem cartItem = new CartItem(1L, 2L, 3L, "Product", "Brand", 10.0, Arrays.asList("Category1", "Category2"));
        cartItem.setCart(new Cart());
        CartItemEntity cartItemEntity = new CartItemEntity();
        when(cartItemEntityMapper.toEntity(cartItem)).thenReturn(cartItemEntity);

        cartItemJpaAdapter.addCartItem(cartItem);

        verify(cartItemJpaRepository).save(cartItemEntity);
    }

    @DisplayName("Get cart items on cart with valid cart ID")
    @Test
    void getCartItemsOnCartWithValidCartId() {
        Long cartId = 1L;
        List<CartItemEntity> cartItemEntities = Arrays.asList(new CartItemEntity());
        List<CartItem> cartItems = Arrays.asList(new CartItem());
        when(cartItemJpaRepository.findCartItemEntityByCartId(cartId)).thenReturn(cartItemEntities);
        when(cartItemEntityMapper.toDomainList(cartItemEntities)).thenReturn(cartItems);

        List<CartItem> result = cartItemJpaAdapter.getCartItemsOnCart(cartId);

        assertEquals(cartItems, result);
    }

}