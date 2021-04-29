package by.tms.diploma.service.impl;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import by.tms.diploma.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private UserDetails userDetails;
    private User user;

    private final String USERNAME = "Admin";
    private final String PASSWORD = "admin";
    private final List<UserRole> ROLE = Arrays.asList(UserRole.ADMIN, UserRole.MODERATOR);

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setRoles(ROLE);

        userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .authorities(ROLE)
                .build();
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        UserDetails actual = userDetailsService.loadUserByUsername(USERNAME);
        assertEquals(userDetails, actual);
    }
}