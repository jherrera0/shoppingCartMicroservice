package bootcamp.shopping_cart_microservice.infrastructure.exceptionhandler;

import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FeignExceptionHandlerTest {

    private FeignExceptionHandler feignExceptionHandler;
    private ErrorDecoder.Default defaultErrorDecoder;

    @BeforeEach
    void setUp() {
        feignExceptionHandler = new FeignExceptionHandler();
        defaultErrorDecoder = new ErrorDecoder.Default();
    }

    @Test
    @DisplayName("getErrorMessage should return correct message for valid response")
    void getErrorMessageShouldReturnCorrectMessageForValidResponse() {
        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        String errorMessage = feignExceptionHandler.getErrorMessage(response);

        assertEquals("Not Found", errorMessage);
    }

    @Test
    @DisplayName("getErrorMessage should return error message from response body")
    void getErrorMessageShouldReturnErrorMessageFromResponseBody() {
        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .body("Detailed error message", StandardCharsets.UTF_8)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        String errorMessage = feignExceptionHandler.getErrorMessage(response);

        assertEquals("Detailed error message", errorMessage);
    }

    @Test
    @DisplayName("getErrorMessage should return error reading response body message")
    void getErrorMessageShouldReturnErrorReadingResponseBodyMessage() {
        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .body((Response.Body) null)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        String errorMessage = feignExceptionHandler.getErrorMessage(response);

        assertEquals("Internal Server Error", errorMessage);
    }

    @Test
    @DisplayName("getErrorMessage should return default message for empty reason")
    void getErrorMessageShouldReturnDefaultMessageForEmptyReason() {
        Response response = Response.builder()
                .status(400)
                .reason(null)
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        String errorMessage = feignExceptionHandler.getErrorMessage(response);

        assertEquals("Unknown error occurred", errorMessage);
    }
    @Test
    @DisplayName("decode should return ResponseStatusException with BAD_REQUEST for 400 status")
    void decodeShouldReturnBadRequestFor400Status() {
        Response response = Response.builder()
                .status(400)
                .reason("Bad Request")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = feignExceptionHandler.decode("GET", response);

        assertInstanceOf(ResponseStatusException.class, exception);
        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseStatusException) exception).getStatusCode());
        assertEquals(feignExceptionHandler.decode("GET",response).getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("decode should return ResponseStatusException with NOT_FOUND for 404 status")
    void decodeShouldReturnNotFoundFor404Status() {
        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = feignExceptionHandler.decode("GET", response);

        assertInstanceOf(ResponseStatusException.class, exception);
        assertEquals(HttpStatus.NOT_FOUND, ((ResponseStatusException) exception).getStatusCode());
        assertEquals(feignExceptionHandler.decode("GET",response).getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("decode should return ResponseStatusException with INTERNAL_SERVER_ERROR for 500 status")
    void decodeShouldReturnInternalServerErrorFor500Status() {
        Response response = Response.builder()
                .status(500)
                .reason("Internal Server Error")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = feignExceptionHandler.decode("GET", response);

        assertInstanceOf(ResponseStatusException.class, exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((ResponseStatusException) exception).getStatusCode());
        assertEquals(feignExceptionHandler.decode("GET",response).getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("decode should return default exception for unknown status")
    void decodeShouldReturnDefaultExceptionForUnknownStatus() {
        Response response = Response.builder()
                .status(418)
                .reason("I'm a teapot")
                .request(Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), null, StandardCharsets.UTF_8))
                .build();

        Exception exception = feignExceptionHandler.decode("GET", response);

        assertFalse(exception instanceof ResponseStatusException);
        assertEquals(feignExceptionHandler.decode("GET",response).getMessage(), exception.getMessage());
    }
}