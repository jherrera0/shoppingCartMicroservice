package bootcamp.shopping_cart_microservice.application.http.handler.interfaces;

import bootcamp.shopping_cart_microservice.application.http.dto.request.addArticleRequest;

public interface ICartHandler {
    void addItem(String token, addArticleRequest request);
}
