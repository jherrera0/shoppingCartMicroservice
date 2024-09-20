package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService myUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(JwtConst.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(JwtConst.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
       try {
           String jwt = authHeader.substring(JwtConst.SUB_STRING_INDEX);
           UserDetails user = myUserDetailsService.loadUserByUsername(jwt);
           UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authToken);
       } catch (Exception e) {
           SecurityContextHolder.clearContext();
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
           return;
       }

        filterChain.doFilter(request, response);
    }
}