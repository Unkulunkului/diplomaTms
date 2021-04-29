package by.tms.diploma.repository;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User getById (long id);
    User getByUsername (String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAllByRolesContains(UserRole role);
}
