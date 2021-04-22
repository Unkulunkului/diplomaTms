package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourAddModel {
    @Size(max = 3000, message = "Max size - 3000")
    private String description;
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,50}", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 50!")
    private String name;
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,20}", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 15!")
    private String hotelName;
    @Pattern(regexp = "\\d{1,6}(\\.\\d{1,2})?", message = "Incorrect price")
    private String price;
    private String typeOfRest;
    private List<String> visitedCountries;
    @Pattern(regexp = "\\d+", message = "Only numbers")
    private String tourDuration;
    @Pattern(regexp = "\\d+", message = "Only numbers")
    private String dayAtSea;
}
