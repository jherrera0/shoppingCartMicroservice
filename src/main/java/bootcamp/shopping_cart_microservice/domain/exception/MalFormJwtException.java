package bootcamp.shopping_cart_microservice.domain.exception;

public class MalFormJwtException extends RuntimeException {
    public MalFormJwtException(String message) {
        super(message);
    }
}
