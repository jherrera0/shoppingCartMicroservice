package bootcamp.shopping_cart_microservice.infrastructure.configuration.feign;

import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import bootcamp.shopping_cart_microservice.domain.until.TokenHolder;
import bootcamp.shopping_cart_microservice.infrastructure.exceptionhandler.FeignExceptionHandler;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeignClientsConfigTest {
    @Test
    void requestInterceptor_WithValidToken_AddsAuthorizationHeader() {
        TokenHolder.setToken("validToken");
        RequestTemplate requestTemplate = new RequestTemplate();

        FeignClientsConfig.requestInterceptor().apply(requestTemplate);

        assertEquals("validToken", requestTemplate.headers().get(JwtConst.AUTHORIZATION).iterator().next());
    }

    @Test
    void requestInterceptor_WithNullToken_DoesNotAddAuthorizationHeader() {
        TokenHolder.setToken(null);
        RequestTemplate requestTemplate = new RequestTemplate();

        FeignClientsConfig.requestInterceptor().apply(requestTemplate);

        assertFalse(requestTemplate.headers().containsKey(JwtConst.AUTHORIZATION));
    }

    @Test
    void requestInterceptor_WithEmptyToken_DoesNotAddAuthorizationHeader() {
        TokenHolder.setToken("");
        RequestTemplate requestTemplate = new RequestTemplate();

        FeignClientsConfig.requestInterceptor().apply(requestTemplate);

        assertFalse(requestTemplate.headers().containsKey(JwtConst.AUTHORIZATION));
    }

    @Test
    void feignErrorDecoder_ReturnsFeignExceptionHandler() {
        ErrorDecoder errorDecoder = new FeignClientsConfig().feignErrorDecoder();

        assertInstanceOf(FeignExceptionHandler.class, errorDecoder);
    }
}