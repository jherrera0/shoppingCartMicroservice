package bootcamp.shopping_cart_microservice.domain.spi;

import bootcamp.shopping_cart_microservice.domain.model.CartItem;

import java.util.List;

public interface ICartItemPersistencePort {
    void updateCartItem(CartItem cartItem);
    void addCartItem(CartItem cartItem);

    List<CartItem> getCartItemsOnCart(Long cartId);
}
