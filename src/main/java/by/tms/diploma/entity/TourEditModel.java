package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourEditModel {
    @Size(max = 1500, message = "Max size - 1500")
    private String description;
    @Pattern(regexp = "([A-Za-z0-9\\s]{5,50})|(^$)", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 50!")
    private String name;
    @Pattern(regexp = "([A-Za-z0-9\\s]{5,20})|(^$)", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 15!")
    private String hotelName;
    @Pattern(regexp = "(\\d{1,5}(\\.\\d{1,2})?)|(^$)", message = "Incorrect price")
    private String price;
    private List<MultipartFile> images;
    private TypeOfRest typeOfRest;
    private String visitedCountries;
    @Pattern(regexp = "(\\d+)|(^$)", message = "Only numbers")
    private String tourDuration;
    @Pattern(regexp = "(\\d+)|(^$)", message = "Only numbers")
    private String dayAtSea;
}
