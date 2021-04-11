package by.tms.diploma.storage;

import by.tms.diploma.entity.ClientRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRequestStorage implements RequestStorage {
    private List<ClientRequest> basketRequest = new ArrayList<>();
    private long gen = 1;

    @Override
    public void addRequest(ClientRequest clientRequest){
        clientRequest.setId(gen++);
        basketRequest.add(clientRequest);
    }
    @Override
    public List<ClientRequest> getAllRequest(){
        return new ArrayList<>(basketRequest);
    }
    @Override
    public void deleteRequest(ClientRequest clientRequest){
        basketRequest.remove(clientRequest);
    }

    @Override
    public Optional<ClientRequest> findById(long id) {
        ClientRequest client = null;
        for (ClientRequest clientRequest : basketRequest) {
            if (clientRequest.getId()==id) {
                client = clientRequest;
            }
        }
        return Optional.ofNullable(client);
    }

}
