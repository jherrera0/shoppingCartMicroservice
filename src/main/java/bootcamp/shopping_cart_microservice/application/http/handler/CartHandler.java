package bootcamp.shopping_cart_microservice.application.http.handler;

import bootcamp.shopping_cart_microservice.application.http.dto.request.AddArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.dto.request.DeleteArticleRequest;
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
    public void addItem(String token , AddArticleRequest request) {
        TokenHolder.setToken(token);
        cartServicePort.addItem(request.getProductId(), request.getQuantity());
        TokenHolder.clear();
    }

    @Override
    public void removeItem(String Token, DeleteArticleRequest request) {
        TokenHolder.setToken(Token);
        cartServicePort.removeItem(request.getProductId());
        TokenHolder.clear();
    }

}
