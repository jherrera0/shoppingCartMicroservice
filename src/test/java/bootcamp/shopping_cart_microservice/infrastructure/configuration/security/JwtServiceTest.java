package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import bootcamp.shopping_cart_microservice.domain.exception.MalFormJwtException;
import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Value("${app-security-key}")
    private String secretKey = "mysecretkeymysecretkeymysecretkey";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void extractUsername_InvalidJwt_ThrowsException() {
        String jwt = "invalidJwt";

        assertThrows(Exception.class, () -> jwtService.extractUsername(jwt));
    }

    @Test
    void extractRole_InvalidJwt_ThrowsException() {
        String jwt = "invalidJwt";

        assertThrows(Exception.class, () -> jwtService.extractRole(jwt));
    }

    @Test
    void generateKey_ReturnsValidKey() {
        Key key = jwtService.generateKey();

        assertNotNull(key);
        assertEquals(Keys.hmacShaKeyFor(secretKey.getBytes()).getAlgorithm(), key.getAlgorithm());
    }
    @Test
    void extractUsername_ValidJwt_ReturnsUsername() {
        String jwt = Jwts.builder().setSubject("testUser").signWith(jwtService.generateKey()).compact();

        String username = jwtService.extractUsername(jwt);

        assertEquals("testUser", username);
    }

    @Test
    void extractRole_ValidJwt_ReturnsRole() {
        String jwt = Jwts.builder().claim(JwtConst.ROLE, "ROLE_USER").signWith(jwtService.generateKey()).compact();

        String role = jwtService.extractRole(jwt);

        assertEquals("ROLE_USER", role);
    }

    @Test
    void extractUserId_ValidJwt_ReturnsUserId() {
        String jwt = Jwts.builder().claim(JwtConst.USER_ID, "12345").signWith(jwtService.generateKey()).compact();

        Long userId = jwtService.extractUserId(jwt);

        assertEquals(12345L, userId);
    }

    @Test
    void extractAllClaims_ValidJwt_ReturnsClaims() {
        String jwt = Jwts.builder().setSubject("testUser").claim(JwtConst.ROLE, "ROLE_USER").claim(JwtConst.USER_ID, "12345").signWith(jwtService.generateKey()).compact();

        Claims claims = jwtService.extractAllClaims(jwt);

        assertEquals("testUser", claims.getSubject());
        assertEquals("ROLE_USER", claims.get(JwtConst.ROLE));
        assertEquals("12345", claims.get(JwtConst.USER_ID));
    }
    
    @Test
    void extractUsername_InvalidJwt_ThrowsMalFormJwtException() {
        String jwt = "invalidJwt";

        assertThrows(MalFormJwtException.class, () -> jwtService.extractUsername(jwt));

    }

}