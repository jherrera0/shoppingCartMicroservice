package bootcamp.shopping_cart_microservice.infrastructure.configuration.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ValidJwt_ReturnsUserDetails() {
        String jwt = "validJwt";
        String username = "user";
        String role = "ROLE_USER";

        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(jwtService.extractRole(jwt)).thenReturn(role);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwt);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role)));
    }

    @Test
    void loadUserByUsername_InvalidJwt_ThrowsUsernameNotFoundException() {
        String jwt = "invalidJwt";

        when(jwtService.extractUsername(jwt)).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername(jwt));
    }

}