package bootcamp.shopping_cart_microservice.domain.spi;

public interface IJwtPersistencePort {
    Long getUserId(String jwt);
    String getUserName(String jwt);
}
