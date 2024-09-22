package bootcamp.shopping_cart_microservice.domain.exception;

public class StockNotEnoughException extends RuntimeException {
    public StockNotEnoughException(String message) {
        super(message);
    }
}
