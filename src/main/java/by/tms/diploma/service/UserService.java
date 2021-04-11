package by.tms.diploma.service;

import by.tms.diploma.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    boolean isEmailAlreadyExists(String email);
    boolean isUsernameAlreadyExists(String email);
    Optional<User> getByUsername(String username);

}
