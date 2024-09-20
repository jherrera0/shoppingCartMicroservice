package bootcamp.shopping_cart_microservice.domain.spi;

import bootcamp.shopping_cart_microservice.domain.model.Cart;

public interface ICartPersistencePort {
    void updateCart(Cart cart);
    Cart getCartByUserId(Long userId);
    void createCart(Long userId);
}
