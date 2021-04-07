package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelAddModel {
    @NotBlank(message = "Enter something...")
    @Pattern(regexp = "[A-Za-z0-9\\s]{5,20}", message = "Name may have only upper and lower case and digits. " +
            "Min length - 5, max - 15!")
    private String name;
    @Pattern(regexp = "[A-Z][a-z-]{3,20}", message = "First letter is upper case. You may use only letter and '-'. " +
            "Min length - 3, max - 20")
    private String country;
    @Pattern(regexp = "[1-5]", message = "Min - 1, max -5")
    private String stars;
    @Size(max = 1500, message = "Max size - 1500")
    private String description;
    private String images;
    private boolean pets;
}
