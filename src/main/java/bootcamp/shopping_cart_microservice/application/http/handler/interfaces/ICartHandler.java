package bootcamp.shopping_cart_microservice.application.http.handler.interfaces;

import bootcamp.shopping_cart_microservice.application.http.dto.request.AddArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.dto.request.DeleteArticleRequest;

public interface ICartHandler {
    void addItem(String token, AddArticleRequest request);
    void removeItem(String token, DeleteArticleRequest request);
}
