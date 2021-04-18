package by.tms.diploma.storage;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCountryStorage implements CountryStorage{
    private List<String> countries = new ArrayList<>();

    {
        countries.add("Netherlands");
        countries.add("Turkey");
        countries.add("Turkmenistan");
        countries.add("Kazakhstan");
        countries.add("Greece");
        countries.add("Belarus");
        countries.add("France");
    }


    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countries);
    }
}
