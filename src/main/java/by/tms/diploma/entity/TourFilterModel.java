package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourFilterModel {
    private String startPrice;
    private String finishPrice;
    private String tourDuration;
    private String dayAtSea;
    private String typeOfRest;
    private String hotelName;
}
