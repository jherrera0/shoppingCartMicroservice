package bootcamp.shopping_cart_microservice.infrastructure.configuration.beanconfiguration;

import bootcamp.shopping_cart_microservice.application.feign.IFeignStockClient;
import bootcamp.shopping_cart_microservice.application.feign.IFeignSupplyClient;
import bootcamp.shopping_cart_microservice.application.jpa.adapter.*;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartItemEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartItemRepository;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartRepository;
import bootcamp.shopping_cart_microservice.domain.api.ICartServicePort;
import bootcamp.shopping_cart_microservice.domain.spi.*;
import bootcamp.shopping_cart_microservice.domain.usecase.CartCase;
import bootcamp.shopping_cart_microservice.infrastructure.configuration.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICartEntityMapper cartEntityMapper;
    private final ICartItemEntityMapper cartItemEntityMapper;
    private final ICartRepository cartRepository;
    private final IFeignStockClient feignStockClient;
    private final IFeignSupplyClient feignSupplyClient;
    private final JwtService jwtService;
    private final ICartItemRepository cartItemRepository;

    @Bean
    public ICartServicePort cartServicePort() {
        return new CartCase(cartPersistencePort(), jwtPersistencePort(), FeignStockPersistencePort(), FeignSupplyPersistencePort(),cartItemPersistencePort());
    }

    @Bean
    public ICartItemPersistencePort cartItemPersistencePort() {
        return new CartItemJpaAdapter(cartItemRepository,cartItemEntityMapper);
    }

    @Bean
    public ICartPersistencePort cartPersistencePort() {
        return new  CartJpaAdapter(cartRepository,cartEntityMapper);
    }

    @Bean
    public IFeignStockPersistencePort FeignStockPersistencePort() {
        return new FeignStockJpaAdapter(feignStockClient);
    }

    @Bean
    public IFeignSupplyPersistencePort FeignSupplyPersistencePort() {
        return new FeignSupplyJpaAdapter(feignSupplyClient);
    }

    @Bean
    public IJwtPersistencePort jwtPersistencePort() {
        return new JwtJpaAdapter(jwtService);
    }

}
