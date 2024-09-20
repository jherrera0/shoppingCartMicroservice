package bootcamp.shopping_cart_microservice.application.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class addArticleRequest {
    @NotNull
    @Positive
    private Long productId;
    @NotNull
    @Positive
    private Long quantity;

    public addArticleRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public addArticleRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
