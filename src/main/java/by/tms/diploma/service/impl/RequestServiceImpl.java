package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.entity.ClientRequestStatusEnum;
import by.tms.diploma.repository.RequestRepository;
import by.tms.diploma.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestStorage;

    @Override
    public ClientRequest save(ClientRequest clientRequest) {
        log.info("Save client request: "+clientRequest);
        return requestStorage.save(clientRequest);
    }

    @Override
    public List<ClientRequest> getAllRequest() {
        log.info("Find all client requests");
        return requestStorage.findAll();
    }

    @Override
    public Optional<ClientRequest> findById(long id) {
        log.info("Find client request by id(id = "+id+")");
        return requestStorage.findById(id);
    }

    @Override
    public void updateStatusById(long id, ClientRequestStatusEnum status) {
        log.info("Update client request status by id(id="+id+") with status"+status);
        Optional<ClientRequest> clientRequestById = requestStorage.findById(id);
        if (clientRequestById.isPresent()) {
            log.info("Client request with id="+id+" exists");
            ClientRequest clientRequest = clientRequestById.get();
            clientRequest.setRequestStatus(status);
            requestStorage.save(clientRequest);
        }
    }

    @Override
    public void setCuratorIdById(long id, long curatorId) {
        log.info("Set curator id(id=)"+curatorId+" in request with id="+id);
        Optional<ClientRequest> clientRequestById = requestStorage.findById(id);
        if (clientRequestById.isPresent()) {
            log.info("Client request with id="+id+" exists");
            ClientRequest clientRequest = clientRequestById.get();
            clientRequest.setCuratorId(curatorId);
            requestStorage.save(clientRequest);
        }
    }
}
