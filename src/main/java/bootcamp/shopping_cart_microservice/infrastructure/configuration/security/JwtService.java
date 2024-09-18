package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import bootcamp.shopping_cart_microservice.domain.exception.MalFormJwtException;
import bootcamp.shopping_cart_microservice.domain.until.ExceptionConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;

public class JwtService {
    @Value("${app-security-key}")
    private String secretKey = "mysecretkeymysecretkeymysecretkeymy";

    public String extractUsername(String jwt) {
        try {
            return extractAllClaims(jwt).getSubject();
        } catch (Exception e) {
            throw new MalFormJwtException(ExceptionConst.MALFORM_JWT_EXCEPTION);
        }
    }
    public String extractRole(String jwt){
        return extractAllClaims(jwt).get("Role").toString();
    }

    public Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new MalFormJwtException(ExceptionConst.MALFORM_JWT_EXCEPTION);
        }
    }

    Key generateKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
