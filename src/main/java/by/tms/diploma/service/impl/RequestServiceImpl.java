package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.entity.ClientRequestStatusEnum;

import by.tms.diploma.entity.UserRole;
import by.tms.diploma.repository.RequestRepository;
import by.tms.diploma.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestStorage;

    @Override
    public void save(ClientRequest clientRequest) {
        requestStorage.save(clientRequest);
    }

    @Override
    public List<ClientRequest> getAllRequest() {
        return requestStorage.findAll();
    }

    @Override
    public Optional<ClientRequest> findById(long id) {
        return requestStorage.findById(id);
    }

    @Override
    public void updateStatusById(long id, ClientRequestStatusEnum status) {
        Optional<ClientRequest> clientRequestById = requestStorage.findById(id);
        if (clientRequestById.isPresent()) {
            ClientRequest clientRequest = clientRequestById.get();
            clientRequest.setRequestStatus(status);
            requestStorage.save(clientRequest);
        }
    }

    @Override
    public void setCuratorIdById(long id, long curatorId) {
        Optional<ClientRequest> clientRequestById = requestStorage.findById(id);
        if (clientRequestById.isPresent()) {
            ClientRequest clientRequest = clientRequestById.get();
            clientRequest.setCuratorId(curatorId);
            requestStorage.save(clientRequest);
        }
    }
}
