package by.tms.diploma.service.impl;


import by.tms.diploma.entity.User;
import by.tms.diploma.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User '"+username+"' try authentication");
        Optional<User> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()){
            User user = byUsername.get();
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRoles())
                    .build();
            log.info("Success authentication");
            return userDetails;
        }
        log.info("Incorrect username");
        throw new UsernameNotFoundException("User '"+username+"' not found");
    }
}
