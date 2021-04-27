package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourAddModel {
    @Size(max = 3000, message = "Max size - 3000")
    private String description;
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,50}", message = "Only upper/lower case and digits. " +
            "Min length - 5, max - 50!")
    private String name;
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,20}", message = "Only upper/lower case and digits. " +
            "Min length - 5, max - 15!")
    private String hotelName;
    @Pattern(regexp = "\\d{1,6}(\\.\\d{1,2})?", message = "Incorrect price")
    private String price;
    private String typeOfRest;
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z,\\s]{5,100}", message = "Only upper/lower case, ','.  " +
            "Min length - 5, max - 100!")
    private String visitedCountries;
    @Pattern(regexp = "\\d+", message = "Only numbers")
    private String tourDuration;
    @Pattern(regexp = "\\d+", message = "Only numbers")
    private String dayAtSea;
}
