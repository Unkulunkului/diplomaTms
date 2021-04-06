package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TourAddModel {
    private String description;
    private String name;
    private String hotelName;
    private double price;
    private List<String> images;
    private String country;
    private boolean withChildren;
}
