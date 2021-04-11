package by.tms.diploma.service.impl;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import by.tms.diploma.repository.UserRepository;

import by.tms.diploma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        List<UserRole> rolesRoles = new ArrayList<>();
        rolesRoles.add(UserRole.USER);
        rolesRoles.add(UserRole.MODERATOR);
        user.setRoles(rolesRoles);
        User save = userRepository.save(user);
        log.info("User "+user.getUsername()+" was saved"); //mark as service
        log.info(save.toString()); //delete
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameAlreadyExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
