package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TourAddModel {
    @Size(max = 1500, message = "Max size - 1500")
    private String description;
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,20}", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 20!")
    private String name;
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,20}", message = "Hotel name may have only upper and lower case and digits. " +
            "Min length - 5, max - 20!")
    private String hotelName;
    @Pattern(regexp = "\\d{1,5}(\\.\\d{1,2})?", message = "Wrong price")
    private String price;
    private String images;
    @Pattern(regexp = "[A-Z][a-z-]{3,15}", message = "First letter is upper case. You may use only letter and '-'. " +
            "Min length - 3, max - 15")
    private String country;
    private boolean withChildren;
}