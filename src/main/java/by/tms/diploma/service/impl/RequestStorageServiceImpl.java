package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.repository.RequestRepository;
import by.tms.diploma.service.RequestStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestStorageServiceImpl implements RequestStorageService {

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
}
