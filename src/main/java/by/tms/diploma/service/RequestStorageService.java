package by.tms.diploma.service;

import by.tms.diploma.entity.ClientRequest;

import java.util.List;
import java.util.Optional;

public interface RequestStorageService {
    void save(ClientRequest clientRequest);

    List<ClientRequest> getAllRequest();

    Optional<ClientRequest> findById(long id);
}
