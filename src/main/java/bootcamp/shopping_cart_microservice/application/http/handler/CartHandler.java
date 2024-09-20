package bootcamp.shopping_cart_microservice.application.http.handler;

import bootcamp.shopping_cart_microservice.application.http.dto.request.addArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import bootcamp.shopping_cart_microservice.domain.api.ICartServicePort;
import bootcamp.shopping_cart_microservice.domain.until.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandler implements ICartHandler {
    private final ICartServicePort cartServicePort;


    @Override
    public void addItem(String token ,addArticleRequest request) {
        TokenHolder.setToken(token);
        cartServicePort.addItem(request.getProductId(), request.getQuantity());
        TokenHolder.clear();
    }
}
