package bootcamp.shopping_cart_microservice.application.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DeleteArticleRequest {
    @NotNull
    @Positive
    private Long productId;

    public DeleteArticleRequest(Long productId) {
        this.productId = productId;
    }

    public DeleteArticleRequest() {
    }

}
