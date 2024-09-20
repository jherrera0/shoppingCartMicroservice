package bootcamp.shopping_cart_microservice.domain.spi;

public interface IFeignSupplyPersistencePort {
    String getNextSupplyDate(Long productId);
}
