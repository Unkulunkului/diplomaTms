package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.entity.ClientRequestStatusEnum;
import by.tms.diploma.entity.Hotel;
import by.tms.diploma.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RequestServiceImplTest {


    @Autowired
    private RequestRepository repository;

    private ClientRequest clientRequest;

    @BeforeEach
    void setUp() {
        clientRequest = new ClientRequest();
        clientRequest.setName("Name");
        clientRequest.setCuratorId(129);
        clientRequest.setRequestStatus(ClientRequestStatusEnum.IN_PROGRESS);
    }

    @Test
    void save() {
        ClientRequest expected = clientRequest;
        ClientRequest actual = repository.save(clientRequest);
        assertEquals(expected, actual);
    }

    @Test
    void getAllRequest() {
        ClientRequest secondRequest = new ClientRequest();
        secondRequest.setName("second name");
        ClientRequest thirdRequest = new ClientRequest();
        thirdRequest.setName("third name");
        ClientRequest [] expected = new ClientRequest[3];
        expected[0] = (clientRequest);
        expected[1] = (secondRequest);
        expected[2] = (thirdRequest);
        List<ClientRequest> clientRequests = repository.saveAll(Arrays.asList(expected));
        ClientRequest[] actual = clientRequests.toArray(new ClientRequest[0]);
        assertArrayEquals(expected, actual);
    }

    @Test
    void findById() {
        ClientRequest expected = clientRequest;
        ClientRequest save = repository.save(expected);
        long id = save.getId();
        Optional<ClientRequest> clientRequest = repository.findById(id);
        ClientRequest actual = new ClientRequest();
        if (clientRequest.isPresent()) {
            actual = clientRequest.get();
        }
        assertEquals(expected, actual);
    }

    @Test
    void updateStatusById() {
        ClientRequest save = repository.save(clientRequest);

        long id = save.getId();
        ClientRequestStatusEnum expected = ClientRequestStatusEnum.DONE;

        Optional<ClientRequest> clientRequest = repository.findById(id);
        if (clientRequest.isPresent()) {
            ClientRequest fromRepository = clientRequest.get();
            fromRepository.setRequestStatus(expected);
            repository.save(fromRepository);
        }

        ClientRequestStatusEnum actual = ClientRequestStatusEnum.WAITING;
        Optional<ClientRequest> changedClientRequest = repository.findById(id);
        if (changedClientRequest.isPresent()) {
            ClientRequest fromRepository = clientRequest.get();
            actual = fromRepository.getRequestStatus();
        }

        assertEquals(expected, actual);
    }

    @Test
    void setCuratorIdById() {
        ClientRequest save = repository.save(clientRequest);

        long id = save.getId();
        long expected = 12;

        Optional<ClientRequest> clientRequest = repository.findById(id);
        if (clientRequest.isPresent()) {
            ClientRequest fromRepository = clientRequest.get();
            fromRepository.setCuratorId(expected);
            repository.save(fromRepository);
        }

        long actual = 0;
        Optional<ClientRequest> changedClientRequest = repository.findById(id);
        if (changedClientRequest.isPresent()) {
            ClientRequest fromRepository = clientRequest.get();
            actual = fromRepository.getCuratorId();
        }

        assertEquals(expected, actual);
    }
}