package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartEntity;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartRepository;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartJpaAdapterTest {

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private ICartEntityMapper cartEntityMapper;

    @InjectMocks
    private CartJpaAdapter cartJpaAdapter;

    public CartJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Update cart with valid data")
    @Test
    void updateCartWithValidData() {
        Cart cart = new Cart();
        CartEntity cartEntity = new CartEntity();
        when(cartEntityMapper.toEntity(cart)).thenReturn(cartEntity);

        cartJpaAdapter.updateCart(cart);

        verify(cartRepository).save(cartEntity);
    }

    @DisplayName("Get cart by user ID when cart exists")
    @Test
    void getCartByUserIdWhenCartExists() {
        Long userId = 1L;
        CartEntity cartEntity = new CartEntity();
        Cart cart = new Cart();
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cartEntity));
        when(cartEntityMapper.toDomain(cartEntity)).thenReturn(cart);

        Cart result = cartJpaAdapter.getCartByUserId(userId);

        assertEquals(cart, result);
    }

    @DisplayName("Get cart by user ID when cart does not exist")
    @Test
    void getCartByUserIdWhenCartDoesNotExist() {
        Long userId = 1L;
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Cart result = cartJpaAdapter.getCartByUserId(userId);

        assertNull(result);
    }

    @DisplayName("Create cart with valid user ID")
    @Test
    void createCartWithValidUserId() {
        Long userId = 1L;

        cartJpaAdapter.createCart(userId);

        verify(cartRepository).save(any(CartEntity.class));
    }
}