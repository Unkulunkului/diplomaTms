package by.tms.diploma.service;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    boolean isEmailAlreadyExists(String email);
    boolean isUsernameAlreadyExists(String email);
    Optional<User> getByUsername(String username);
    Optional<User> getById(long id);
    List<User> getUsersByRole(UserRole role);
    void updateRoleById(long id, UserRole userRole);

}
