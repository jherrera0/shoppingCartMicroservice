package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.feign.IFeignStockClient;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import bootcamp.shopping_cart_microservice.domain.spi.IFeignStockPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignStockJpaAdapter implements IFeignStockPersistencePort {
    private final IFeignStockClient FeignStockClient;

    @Override
    public CartItem findCartItemById(Long id) {
        return FeignStockClient.getArticle(id);
    }
}
