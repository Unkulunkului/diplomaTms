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
    public boolean isEmailExist(String email) {
        log.info("Exist user by email(email = "+email+")");
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameExist(String username) {
        log.info("Exist user by username(username = "+username+")");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("Find user by username(username = "+username+")");
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findById(long id) {
        log.info("Exist user by id(id = "+id+")");
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        log.info("Find users by role(role = "+role+")");
        return userRepository.findAllByRolesContains(role);
    }

    @Override
    public boolean hasRoleById(long id, UserRole role){
        log.info("Checking if user role '"+role+"' exists by id(id = "+id+")");
        User user = userRepository.getById(id);
        List<UserRole> roles = user.getRoles();
        return roles.contains(role);
    }

    @Override
    public boolean hasRoleByUsername(String username, UserRole role){
        log.info("Checking if user role '"+role+"' exists by username(username = "+username+") with user role = ");
        User user = userRepository.getByUsername(username);
        List<UserRole> roles = user.getRoles();
        return roles.contains(role);
    }

    @Override
    public boolean addRoleById(long id, UserRole role){
        log.info("Add user role "+role+"  by id(id = "+id+")");
        User user = userRepository.getById(id);
        List<UserRole> roles = user.getRoles();
        boolean add = roles.add(role);
        userRepository.save(user);
        return add;
    }
    @Override
    public boolean removeRoleById(long id, UserRole role){
        log.info("Remove user role "+role+"  by id(id = "+id+")");
        User user = userRepository.getById(id);
        List<UserRole> roles = user.getRoles();
        boolean remove = roles.remove(role);
        userRepository.save(user);
        return remove;
    }



}
