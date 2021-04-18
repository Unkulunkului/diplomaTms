package by.tms.diploma.service.impl;

import by.tms.diploma.service.InMemoryCountryService;
import by.tms.diploma.storage.CountryStorage;
import by.tms.diploma.storage.InMemoryCountryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryCountryServiceImpl implements InMemoryCountryService {

    @Autowired
    private CountryStorage countryStorage;


    @Override
    public List<String> getCountries() {
        return countryStorage.getCountries();
    }
}
