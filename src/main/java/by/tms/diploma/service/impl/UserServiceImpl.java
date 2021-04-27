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
    public User save(User user) {
        log.info("Save user "+user.getUsername());
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        List<UserRole> rolesRoles = new ArrayList<>();
        rolesRoles.add(UserRole.USER);
        rolesRoles.add(UserRole.MODERATOR);//////////////////////!!!!!!!!!!!!
        user.setRoles(rolesRoles);
        User save = userRepository.save(user);
        log.info("User "+user.getUsername()+" was saved");
        return save;
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        log.info("Exist user by email(email = "+email+")");
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameAlreadyExists(String username) {
        log.info("Exist user by username(username = "+username+")");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        log.info("Find user by username(username = "+username+")");
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getById(long id) {
        log.info("Exist user by id(id = "+id+")");
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        log.info("Find users by role(role = "+role+")");
        return userRepository.findAllByRolesContains(role);
    }

    @Override
    public void updateRoleById(long id, UserRole role) {
        log.info("Update user role by id(id = "+id+") with user role = "+role);
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            List<UserRole> roles = user.getRoles();
            if (!roles.contains(UserRole.ADMIN)) {
                if(roles.contains(role)){
                    roles.remove(role);
                    log.info("role was removed");
                }else {
                    roles.add(role);
                    log.info("role was added");
                }
                userRepository.save(user);
            }
        }
    }


}
