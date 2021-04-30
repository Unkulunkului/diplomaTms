package by.tms.diploma.service.impl;

import by.tms.diploma.entity.ClientRequest;
import by.tms.diploma.entity.ClientRequestStatusEnum;
import by.tms.diploma.entity.Hotel;
import by.tms.diploma.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestServiceImplTest {


    @MockBean
    private RequestRepository repository;


    @Autowired
    private RequestServiceImpl requestService;

    private ClientRequest clientRequest;

    private final String FIRST_REQUEST_NAME = "First name";
    private final String SECOND_REQUEST_NAME = "Second name";
    private final String THIRD_REQUEST_NAME = "Third name";

    private final long FIRST_REQUEST_CURATOR_ID = 122L;
    private final long SECOND_REQUEST_CURATOR_ID = 61L;
    private final long THIRD_REQUEST_CURATOR_ID = 95L;

    private final long FIRST_REQUEST_ID = 1L;

    private final long FIRST_REQUEST_NEW_CURATOR_ID = 11L;

    private final ClientRequestStatusEnum FIRST_REQUEST_OLD_STATUS = ClientRequestStatusEnum.WAITING;
    private final ClientRequestStatusEnum FIRST_REQUEST_NEW_STATUS = ClientRequestStatusEnum.DONE;



    @BeforeEach
    void setUp() {
        clientRequest = new ClientRequest();
        clientRequest.setName(FIRST_REQUEST_NAME);
        clientRequest.setCuratorId(FIRST_REQUEST_CURATOR_ID);
    }

    @Test
    void save() {
        Mockito.when(repository.save(clientRequest)).thenReturn(clientRequest);
        ClientRequest actual = requestService.save(clientRequest);
        assertEquals(clientRequest, actual);
    }

    @Test
    void getAllRequest() {
        ClientRequest secondRequest = new ClientRequest();
        secondRequest.setName(SECOND_REQUEST_NAME);
        secondRequest.setCuratorId(SECOND_REQUEST_CURATOR_ID);

        ClientRequest thirdRequest = new ClientRequest();
        thirdRequest.setName(THIRD_REQUEST_NAME);
        thirdRequest.setCuratorId(THIRD_REQUEST_CURATOR_ID);

        List<ClientRequest> clientRequests = new ArrayList<>();
        clientRequests.add(thirdRequest);
        clientRequests.add(secondRequest);
        clientRequests.add(clientRequest);

        Mockito.when(requestService.getAllRequest()).thenReturn(clientRequests);

        List<ClientRequest> allRequest = requestService.getAllRequest();
        assertEquals(clientRequests.size(), allRequest.size());
    }

    @Test
    void findById() {
        Mockito.when(repository.findById(FIRST_REQUEST_ID)).thenReturn(Optional.of(clientRequest));
        Optional<ClientRequest> requestFromRepository = requestService.findById(1L);
        ClientRequest actual = new ClientRequest();
        if (requestFromRepository.isPresent()) {
            actual = requestFromRepository.get();
        }
        assertEquals(clientRequest, actual);
    }

    @Test
    void updateStatusById() {
        Mockito.when(repository.findById(FIRST_REQUEST_ID)).thenReturn(Optional.of(clientRequest));
        Mockito.spy(repository).save(clientRequest);

        requestService.updateStatusById(FIRST_REQUEST_ID, FIRST_REQUEST_NEW_STATUS);
        Optional<ClientRequest> byId = requestService.findById(FIRST_REQUEST_ID);
        ClientRequestStatusEnum statusEnum = FIRST_REQUEST_OLD_STATUS;
        if (byId.isPresent()) {
            ClientRequest clientRequestFromRepository = byId.get();
            statusEnum = clientRequestFromRepository.getRequestStatus();
        }

        assertEquals(FIRST_REQUEST_NEW_STATUS, statusEnum);
    }

    @Test
    void setCuratorIdById() {
        Mockito.when(repository.findById(FIRST_REQUEST_ID)).thenReturn(Optional.of(clientRequest));
        Mockito.spy(repository).save(clientRequest);

        requestService.setCuratorIdById(FIRST_REQUEST_ID, FIRST_REQUEST_NEW_CURATOR_ID);
        Optional<ClientRequest> byId = requestService.findById(FIRST_REQUEST_ID);
        long requestCuratorId = FIRST_REQUEST_CURATOR_ID;
        if (byId.isPresent()) {
            ClientRequest clientRequestFromRepository = byId.get();
            requestCuratorId = clientRequestFromRepository.getCuratorId();
        }
        assertEquals(FIRST_REQUEST_NEW_CURATOR_ID, requestCuratorId);
    }
}