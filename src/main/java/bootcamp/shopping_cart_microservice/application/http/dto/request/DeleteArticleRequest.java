package bootcamp.shopping_cart_microservice.application.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteArticleRequest {

    @Positive
    private Long productId;


}
