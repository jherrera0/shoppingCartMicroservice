package bootcamp.shopping_cart_microservice.application.feign;

import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service", url = "http://localhost:8082",configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface IFeignStockClient {

    @GetMapping("/article/getArticle")
    public CartItem getArticle(@RequestParam("id") Long productId);
}
