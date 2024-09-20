package bootcamp.shopping_cart_microservice.domain.api;

public interface ICartServicePort {
    void addItem(Long productId, Long quantity);
}
