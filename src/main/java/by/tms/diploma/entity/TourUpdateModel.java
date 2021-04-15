package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourUpdateModel {
    private long id;
    private String name;
    private String pricePerDay;
    private String hotelName;
    private String description;
}
