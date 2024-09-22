package bootcamp.shopping_cart_microservice.application.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "supply-service", url = "http://localhost:8081",configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface IFeignSupplyClient {

    @GetMapping("/supply/getNextSupplyDate")
    String getNextSupplyDate(Long productId);
}
