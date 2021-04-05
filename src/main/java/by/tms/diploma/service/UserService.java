package by.tms.diploma.service;

import by.tms.diploma.entity.User;

public interface UserService {
    void save(User user);
    boolean isEmailAlreadyExists(String email);
    boolean isUsernameAlreadyExists(String email);

}
