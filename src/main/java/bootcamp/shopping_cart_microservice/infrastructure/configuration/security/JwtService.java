package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import bootcamp.shopping_cart_microservice.domain.exception.MalFormJwtException;
import bootcamp.shopping_cart_microservice.domain.until.ExceptionConst;
import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public class JwtService {
    @Value("${app-security-key}")
    private String secretKey = "mySecretKeymysecretkeymySecretKeymysecretkey";

    public String extractUsername(String jwt) {
        try {
            return extractAllClaims(jwt).getSubject();
        } catch (MalformedJwtException e) {
            throw new MalFormJwtException(ExceptionConst.MALFORM_JWT_EXCEPTION);
        }
    }
    public String extractRole(String jwt){
        return extractAllClaims(jwt).get(JwtConst.ROLE).toString();
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
    }

    Key generateKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Long extractUserId(String jwt) {
        return Long.parseLong(extractAllClaims(jwt).get(JwtConst.USER_ID).toString());
    }
}
