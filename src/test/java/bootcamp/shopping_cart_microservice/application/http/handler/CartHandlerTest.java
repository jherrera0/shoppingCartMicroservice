package bootcamp.shopping_cart_microservice.application.http.handler;

import bootcamp.shopping_cart_microservice.application.http.dto.request.AddArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import bootcamp.shopping_cart_microservice.domain.api.ICartServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class CartHandlerTest {

    @Mock
    private ICartServicePort cartServicePort;

    @Mock
    private ICartHandler cartHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartHandler = new CartHandler(cartServicePort);
    }
    @DisplayName("Add item to cart with valid token and request")
    @Test
    void addItemToCartWithValidTokenAndRequest() {
        String token = "validToken";
        AddArticleRequest request = new AddArticleRequest(1L, 2L);

        cartHandler.addItem(token, request);

        verify(cartServicePort).addItem(1L, 2L);
        verifyNoMoreInteractions(cartServicePort);
    }

    @DisplayName("Add item to cart with null request")
    @Test
    void addItemToCartWithNullRequest() {
        String token = "validToken";
        AddArticleRequest request = null;

        assertThrows(NullPointerException.class, () -> cartHandler.addItem(token, request));
    }

}