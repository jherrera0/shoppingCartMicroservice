package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.infrastructure.configuration.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class JwtJpaAdapterTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private JwtJpaAdapter jwtJpaAdapter;

    public JwtJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Get user ID with valid JWT")
    @Test
    void getUserIdWithValidJwt() {
        String jwt = "valid.jwt.token";
        Long expectedUserId = 1L;
        when(jwtService.extractUserId(jwt)).thenReturn(expectedUserId);

        Long result = jwtJpaAdapter.getUserId(jwt);

        assertEquals(expectedUserId, result);
    }


    @DisplayName("Get user ID with invalid JWT")
    @Test
    void getUserIdWithInvalidJwt() {
        String jwt = "invalid.jwt.token";
        when(jwtService.extractUserId(jwt)).thenReturn(null);

        Long result = jwtJpaAdapter.getUserId(jwt);

        assertEquals(null, result);
    }

    @DisplayName("Get user name with valid JWT")
    @Test
    void getUserNameWithValidJwt() {
        String jwt = "valid.jwt.token";
        String expectedUserName = "username";
        when(jwtService.extractUsername(jwt)).thenReturn(expectedUserName);

        String result = jwtJpaAdapter.getUserName(jwt);

        assertEquals(expectedUserName, result);
    }

    @DisplayName("Get user name with null JWT")
    @Test
    void getUserNameWithNullJwt() {
        String jwt = null;

        String result = jwtJpaAdapter.getUserName(jwt);

        assertEquals(null, result);
    }

    @DisplayName("Get user name with invalid JWT")
    @Test
    void getUserNameWithInvalidJwt() {
        String jwt = "invalid.jwt.token";
        when(jwtService.extractUsername(jwt)).thenReturn(null);

        String result = jwtJpaAdapter.getUserName(jwt);

        assertEquals(null, result);
    }
}