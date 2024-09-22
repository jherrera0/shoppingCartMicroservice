package bootcamp.shopping_cart_microservice.application.http.dto.request;

import jakarta.validation.constraints.Positive;

public class AddArticleRequest {
    @Positive
    private Long productId;
    @Positive
    private Long quantity;

    public AddArticleRequest(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public AddArticleRequest() {
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
