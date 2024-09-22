package bootcamp.shopping_cart_microservice.domain.exception;

public class ArticleNotFoundOnCart extends RuntimeException {
    public ArticleNotFoundOnCart(String message) {
        super(message);
    }
}
