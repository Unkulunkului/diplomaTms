package by.tms.diploma.service;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.entity.ClientRequestStatusEnum;


import java.util.List;
import java.util.Optional;

public interface RequestService {

    ClientRequest save(ClientRequest clientRequest);
    List<ClientRequest> getAllRequest();
    Optional<ClientRequest> findById(long id);
    void updateStatusById(long id, ClientRequestStatusEnum status);
    void setCuratorIdById(long id, long curatorId);
}
