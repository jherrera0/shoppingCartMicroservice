package bootcamp.shopping_cart_microservice.infrastructure.controller;

import bootcamp.shopping_cart_microservice.application.http.dto.request.addArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

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
        addArticleRequest request = new addArticleRequest();
        controller.addToCart(token, request);

        verify(cartHandler).addItem(token, request);
    }

    @Test
    void removeFromCart_ReturnsExpectedMessage() {
        String expectedMessage = "Remove from cart logic";
        String actualMessage = controller.removeFromCart().getBody();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void buyCart_ReturnsExpectedMessage() {
        String expectedMessage = "Buy cart logic";
        String actualMessage = controller.buyCart().getBody();
        assertEquals(expectedMessage, actualMessage);
    }

}