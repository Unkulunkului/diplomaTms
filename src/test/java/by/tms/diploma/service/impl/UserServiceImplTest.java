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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private final long ID = 1L;
    private final String USERNAME = "User";
    private final String PASSWORD = "Password";
    private final String EMAIL = "Email";
    private final String SECRET_SENTENCE = "Super sentence";
    private final List<UserRole> ROLES = new ArrayList<>();
    private final UserRole NEW_ROLE = UserRole.USER;
    private final UserRole REMOVABLE_ROLE = UserRole.MODERATOR;

    private final String NEW_PASSWORD = "New password";
    private final String ENCODE_PASSWORD = "JFwfkl12e1AWD22knk";

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        ROLES.add(UserRole.MODERATOR);
        ROLES.add(UserRole.ADMIN);
        user.setRoles(ROLES);
        user.setSecretSentence(SECRET_SENTENCE);
    }

    @Test
    void save() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User actual = userService.save(user);
        assertEquals(user, actual);
    }

    @Test
    void isEmailExist() {
        Mockito.when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
        boolean actual = userService.isEmailExist(EMAIL);
        assertTrue(actual);
    }

    @Test
    void isUsernameExist() {
        Mockito.when(userRepository.existsByUsername(USERNAME)).thenReturn(true);
        boolean actual = userService.isUsernameExist(USERNAME);
        assertTrue(actual);
    }

    @Test
    void findByUsername() {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        Optional<User> userFromRepository = userService.findByUsername(USERNAME);
        User actual = new User();
        if(userFromRepository.isPresent()){
            actual = userFromRepository.get();
        }
        assertEquals(this.user, actual);
    }

    @Test
    void getByUsername() {
        Mockito.when(userRepository.getByUsername(USERNAME)).thenReturn(user);
        User actual = userService.getByUsername(USERNAME);
        assertEquals(this.user, actual);
    }

    @Test
    void findById() {
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        Optional<User> userFromRepository = userService.findById(ID);
        User actual = new User();
        if(userFromRepository.isPresent()){
            actual = userFromRepository.get();
        }
        assertEquals(this.user, actual);
    }

    @Test
    void hasRoleById() {
        Mockito.when(userRepository.getById(ID)).thenReturn(user);
        boolean actual = userService.hasRoleById(ID, UserRole.MODERATOR);
        assertTrue(actual);
    }

    @Test
    void hasRoleByUsername() {
        Mockito.when(userRepository.getByUsername(USERNAME)).thenReturn(user);
        boolean actual = userService.hasRoleByUsername(USERNAME, UserRole.MODERATOR);
        assertTrue(actual);
    }

    @Test
    void addRoleById() {
        Mockito.when(userRepository.getById(ID)).thenReturn(user);
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        Mockito.spy(userRepository).save(user);
        userService.addRoleById(ID, NEW_ROLE);
        Optional<User> byId = userService.findById(ID);
        User userFromRepository = new User();
        userFromRepository.setRoles(ROLES);
        if(byId.isPresent()){
            userFromRepository = byId.get();
        }
        assertTrue(userFromRepository.getRoles().contains(NEW_ROLE));
    }

    @Test
    void removeRoleById() {
        Mockito.when(userRepository.getById(ID)).thenReturn(user);
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        Mockito.spy(userRepository).save(user);
        userService.removeRoleById(ID, REMOVABLE_ROLE);
        Optional<User> byId = userService.findById(ID);
        User userFromRepository = new User();
        userFromRepository.setRoles(ROLES);
        if(byId.isPresent()){
            userFromRepository = byId.get();
        }
        assertFalse(userFromRepository.getRoles().contains(REMOVABLE_ROLE));
    }

    @Test
    void changePassword() {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn(ENCODE_PASSWORD);
        Mockito.spy(userRepository).save(user);

        User newPasswordUser = new User();
        newPasswordUser.setPassword(NEW_PASSWORD);
        newPasswordUser.setEmail(EMAIL);
        newPasswordUser.setUsername(USERNAME);
        newPasswordUser.setSecretSentence(SECRET_SENTENCE);

        userService.changePassword(newPasswordUser);
        Optional<User> byUsername = userService.findByUsername(USERNAME);
        String actual = "";
        if(byUsername.isPresent()){
            User userFromRepository = byUsername.get();
            actual = userFromRepository.getPassword();
        }
        assertEquals(ENCODE_PASSWORD, actual);
    }
}