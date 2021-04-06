package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelAddModel {
    private String name;
    private String country;
    private int stars;
    private String description;
    private List<String> images;
    private boolean pets;
}
