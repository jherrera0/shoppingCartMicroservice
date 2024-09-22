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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("Authenticate when Authorization header is valid")
    void authenticateWhenAuthorizationHeaderIsValid() throws ServletException, IOException {
        String validToken = "Bearer validToken";
        when(request.getHeader(JwtConst.AUTHORIZATION)).thenReturn(validToken);
        UserDetails userDetails = mock(UserDetails.class);
        when(myUserDetailsService.loadUserByUsername("validToken")).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(myUserDetailsService).loadUserByUsername("validToken");
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Clear context and send error when exception occurs during authentication")
    void clearContextAndSendErrorWhenExceptionOccursDuringAuthentication() throws ServletException, IOException {
        String invalidToken = "Bearer invalidToken";
        when(request.getHeader(JwtConst.AUTHORIZATION)).thenReturn(invalidToken);
        when(myUserDetailsService.loadUserByUsername("invalidToken")).thenThrow(new RuntimeException("Invalid token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(myUserDetailsService).loadUserByUsername("invalidToken");
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        verify(filterChain, never()).doFilter(request, response);
    }
}