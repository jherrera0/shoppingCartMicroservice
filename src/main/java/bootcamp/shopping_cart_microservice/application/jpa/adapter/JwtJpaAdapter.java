package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.domain.spi.IJwtPersistencePort;
import bootcamp.shopping_cart_microservice.infrastructure.configuration.security.JwtService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtJpaAdapter implements IJwtPersistencePort {
    private final JwtService jwtService;

    @Override
    public Long getUserId(String jwt) {
        return jwtService.extractUserId(jwt);
    }

    @Override
    public String getUserName(String jwt) {
        return jwtService.extractUsername(jwt);
    }
}
