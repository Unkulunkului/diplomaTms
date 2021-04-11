package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.service.RequestStorageService;
import by.tms.diploma.storage.RequestStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestStorageServiceImpl implements RequestStorageService {

    @Autowired
    private RequestStorage requestStorage;

    @Override
    public void addRequest(ClientRequest clientRequest) {
        requestStorage.addRequest(clientRequest);
    }

    @Override
    public List<ClientRequest> getAllRequest() {
        List<ClientRequest> allRequest = requestStorage.getAllRequest();
        return allRequest;
    }

    @Override
    public void deleteRequest(ClientRequest clientRequest) {
        requestStorage.deleteRequest(clientRequest);
    }

    @Override
    public Optional<ClientRequest> findById(long id) {
        Optional<ClientRequest> byId = requestStorage.findById(id);
        return byId;
    }
}
