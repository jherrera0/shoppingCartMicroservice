package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private UserDetailsService myUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(myUserDetailsService);
    }

    @Test
    @DisplayName("Do not authenticate when Authorization header is missing")
    void doNotAuthenticateWhenAuthorizationHeaderIsMissing() throws ServletException, IOException {
        when(request.getHeader(JwtConst.AUTHORIZATION)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull( SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Do not authenticate when Authorization header does not start with Bearer")
    void doNotAuthenticateWhenAuthorizationHeaderDoesNotStartWithBearer() throws ServletException, IOException {
        when(request.getHeader(JwtConst.AUTHORIZATION)).thenReturn("InvalidHeader");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
    
}