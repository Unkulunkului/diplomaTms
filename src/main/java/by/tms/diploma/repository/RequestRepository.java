package by.tms.diploma.repository;

import by.tms.diploma.entity.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<ClientRequest, Long> {
}
