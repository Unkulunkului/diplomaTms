package by.tms.diploma.service;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    boolean isEmailExist(String email);
    boolean isUsernameExist(String username);
    Optional<User> findByUsername(String username);
    User getByUsername(String username);
    Optional<User> findById(long id);
    List<User> getUsersByRole(UserRole role);
    boolean hasRoleById(long id, UserRole role);
    boolean addRoleById(long id, UserRole role);
    boolean removeRoleById(long id, UserRole role);
    boolean hasRoleByUsername(String username, UserRole role);
}
