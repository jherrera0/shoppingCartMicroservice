package bootcamp.shopping_cart_microservice.application.http.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddArticleRequestTest {
    @DisplayName("Create addArticleRequest with valid productId and quantity")
    @Test
    void createAddArticleRequestWithValidProductIdAndQuantity() {
        Long productId = 1L;
        Long quantity = 2L;
        AddArticleRequest request = new AddArticleRequest(productId, quantity);

        assertEquals(productId, request.getProductId());
        assertEquals(quantity, request.getQuantity());
    }

    @DisplayName("Set and get productId")
    @Test
    void setAndGetProductId() {
        AddArticleRequest request = new AddArticleRequest();
        Long productId = 1L;
        request.setProductId(productId);
        assertEquals(productId, request.getProductId());
    }

    @DisplayName("Set and get quantity")
    @Test
    void setAndGetQuantity() {
        AddArticleRequest request = new AddArticleRequest();
        Long quantity = 2L;
        request.setQuantity(quantity);
        assertEquals(quantity, request.getQuantity());
    }

}