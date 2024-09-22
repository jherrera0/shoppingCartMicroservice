package bootcamp.shopping_cart_microservice.infrastructure.controller;

import bootcamp.shopping_cart_microservice.application.http.dto.request.AddArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.dto.request.DeleteArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ShoppingCartRestControllerTest {

    @Mock
    ICartHandler cartHandler;

    @Mock
    ShoppingCartRestController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ShoppingCartRestController(cartHandler);
    }

    @Test
    void addToCart_WithValidTokenAndRequest_CallsAddItem() {
        String token = "validToken";
        AddArticleRequest request = new AddArticleRequest();
        controller.addToCart(token, request);

        verify(cartHandler).addItem(token, request);
    }

    @Test
    void removeFromCart_WithValidTokenAndRequest_CallsRemoveItem() {
        String token = "validToken";
        DeleteArticleRequest request = new DeleteArticleRequest();
        controller.removeFromCart(request, token);

        verify(cartHandler).removeItem(token, request);
    }

    @Test
    void buyCart_WithValidToken_ReturnsOkResponse() {
        ResponseEntity<String> response = controller.buyCart();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Buy cart logic", response.getBody());
    }

}