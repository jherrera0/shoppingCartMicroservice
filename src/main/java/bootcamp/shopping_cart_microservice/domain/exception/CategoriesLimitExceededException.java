package bootcamp.shopping_cart_microservice.domain.exception;

public class CategoriesLimitExceededException extends RuntimeException {
    public CategoriesLimitExceededException(String message) {
        super(message);
    }
}
