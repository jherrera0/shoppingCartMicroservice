package bootcamp.shopping_cart_microservice.domain.spi;

import bootcamp.shopping_cart_microservice.domain.model.CartItem;

public interface IFeignStockPersistencePort {
    CartItem findCartItemById(Long id);
}
