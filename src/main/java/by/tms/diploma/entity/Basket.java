package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    private List<Tour> tours = new ArrayList<>();

    public void addTour(Tour tour){
        tours.add(tour);
    }
}
