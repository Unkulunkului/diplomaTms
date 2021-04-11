package by.tms.diploma.storage;

import by.tms.diploma.entity.ClientRequest;

import java.util.List;
import java.util.Optional;

public interface RequestStorage {

    void addRequest(ClientRequest clientRequest);

    List<ClientRequest> getAllRequest();

    void deleteRequest(ClientRequest clientRequest);

    Optional<ClientRequest> findById(long id);


}
