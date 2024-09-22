package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.feign.IFeignSupplyClient;
import bootcamp.shopping_cart_microservice.domain.spi.IFeignSupplyPersistencePort;

public class FeignSupplyJpaAdapter implements IFeignSupplyPersistencePort {
    private final IFeignSupplyClient feignSupplyClient;

    public FeignSupplyJpaAdapter(IFeignSupplyClient feignSupplyClient) {
        this.feignSupplyClient = feignSupplyClient;
    }

    @Override
    public String getNextSupplyDate(Long productId) {
        return feignSupplyClient.getNextSupplyDate(productId);
    }
}
